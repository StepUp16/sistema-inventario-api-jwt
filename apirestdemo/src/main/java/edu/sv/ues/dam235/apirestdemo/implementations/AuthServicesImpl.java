package edu.sv.ues.dam235.apirestdemo.implementations;

import edu.sv.ues.dam235.apirestdemo.configs.CustomerDetailServices;
import edu.sv.ues.dam235.apirestdemo.dtos.TokenDTO;
import edu.sv.ues.dam235.apirestdemo.dtos.UserRegisterDTO;
import edu.sv.ues.dam235.apirestdemo.entities.User;
import edu.sv.ues.dam235.apirestdemo.repositories.UserRepository;
import edu.sv.ues.dam235.apirestdemo.services.AuthServices;
import edu.sv.ues.dam235.apirestdemo.utilities.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServicesImpl implements AuthServices {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerDetailServices customerDetailServices;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository; // Necesario para guardar el nuevo usuario

    @Autowired
    private PasswordEncoder passwordEncoder; // Necesario para encriptar la clave

    @Override
    public TokenDTO login(String user, String pass) {
        TokenDTO token = new TokenDTO();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user, pass)
            );

            if (authentication.isAuthenticated()) {
                UserDetails usuarioDetail = (UserDetails) authentication.getPrincipal();
                // Verificamos si el usuario está activo en nuestra lógica de negocio
                if (customerDetailServices.getUserDetail().getActive()) {
                    token = jwtUtil.generateToken(user, usuarioDetail);
                    return token;
                }
            }
            return null;
        } catch (BadCredentialsException bad) {
            token.setMsj("Credenciales incorrectas!");
            return null;
        } catch (Exception e) {
            log.error("Error inesperado", e);
            token.setMsj("Error inesperado");
            return null;
        }
    }

    @Override
    public boolean register(UserRegisterDTO userDto) {
        try {
            // Validar si el correo ya existe
            User existingUser = userRepository.findByEmail(userDto.getEmail());
            if (existingUser != null) {
                return false; // El usuario ya existe
            }

            User newUser = new User();
            newUser.setName(userDto.getName());
            newUser.setLastName(userDto.getLastName());
            newUser.setEmail(userDto.getEmail());
            newUser.setActive(true); // Activado por defecto

            // Encriptamos la contraseña antes de guardarla
            // El PasswordEncoder agregará el prefijo {bcrypt} automáticamente si es DelegatingPasswordEncoder
            String encodedPass = passwordEncoder.encode(userDto.getPassword());
            newUser.setPassword(encodedPass);

            userRepository.save(newUser);
            return true;

        } catch (Exception e) {
            log.error("Error al registrar usuario", e);
            return false;
        }
    }
}
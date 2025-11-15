package edu.sv.ues.dam235.apirestdemo.controllers;

import edu.sv.ues.dam235.apirestdemo.dtos.LoginDTO;
import edu.sv.ues.dam235.apirestdemo.dtos.TokenDTO;
import edu.sv.ues.dam235.apirestdemo.dtos.UserRegisterDTO;
import edu.sv.ues.dam235.apirestdemo.services.AuthServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder; // Importante para el log de logout
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    final private AuthServices authServices;

    private AuthController(AuthServices authServices) {
        this.authServices = authServices;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO authRequest) {
        try {
            TokenDTO token = authServices.login(authRequest.getUser(), authRequest.getPass());
            if (token == null) {
                return ResponseEntity.status(401).build();
            } else {
                token.setMsj("Logueado exitosamente");

                // 2. Log en consola del servidor
                log.info("Usuario {} ha iniciado sesión correctamente", authRequest.getUser());

                return ResponseEntity.ok(token);
            }
        } catch (Exception e) {
            log.error("Error en login", e);
            return null;
        }
    }

    // --- ENDPOINT DE REGISTRO ---
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userDto) {
        try {
            boolean success = authServices.register(userDto);
            if (success) {
                log.info("Usuario registrado: {}", userDto.getEmail()); // Log extra
                return ResponseEntity.ok("Usuario registrado con éxito");
            } else {
                return ResponseEntity.badRequest().body("Error: El usuario ya existe o datos inválidos");
            }
        } catch (Exception e) {
            log.error("Error en registro", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // --- ENDPOINT DE LOGOUT ---
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Obtenemos el nombre del usuario actual para el log
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Corregimos el log pasando la variable username
        log.info("Usuario {} ha cerrado sesión correctamente", username);

        return ResponseEntity.ok("{\"message\": \"Logout exitoso. Token eliminado.\"}");
    }
}
package edu.sv.ues.dam235.apirestdemo.services;

import edu.sv.ues.dam235.apirestdemo.dtos.TokenDTO;
import edu.sv.ues.dam235.apirestdemo.dtos.UserRegisterDTO;

public interface AuthServices {
    public TokenDTO login(String user, String pass);

    // Nuevo método para registrar
    public boolean register(UserRegisterDTO userDto);
}
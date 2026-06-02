package org.example.soundly.ServiceIMP;

import org.example.soundly.DTO.AuthResponse;
import org.example.soundly.DTO.LoginRequest;
import org.example.soundly.DTO.RegisterRequest;
import org.example.soundly.Service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceIMP implements AuthService {
    @Override
    public String register(RegisterRequest request) {
        return "Register Method";
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return new AuthResponse("Dummy Token");
    }
}

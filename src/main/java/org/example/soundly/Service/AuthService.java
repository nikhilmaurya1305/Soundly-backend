package org.example.soundly.Service;

import org.example.soundly.DTO.AuthResponse;
import org.example.soundly.DTO.LoginRequest;
import org.example.soundly.DTO.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}

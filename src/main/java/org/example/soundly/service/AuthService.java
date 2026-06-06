package org.example.soundly.service;

import org.example.soundly.dto.AuthResponse;
import org.example.soundly.dto.LoginRequest;
import org.example.soundly.dto.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}

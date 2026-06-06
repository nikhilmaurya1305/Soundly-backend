package org.example.soundly.serviceIMP;

import lombok.RequiredArgsConstructor;
import org.example.soundly.Enum.Role;
import org.example.soundly.dto.AuthResponse;
import org.example.soundly.dto.LoginRequest;
import org.example.soundly.dto.RegisterRequest;
import org.example.soundly.entity.User;
import org.example.soundly.exception.UserAlreadyExistsException;
import org.example.soundly.exception.UserNotFoundException;
import org.example.soundly.repository.UserRepository;
import org.example.soundly.security.JwtService;
import org.example.soundly.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceIMP implements AuthService {

    private final JwtService jwtService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with this email");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.USER);
        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!matches) {
            throw new RuntimeException("Incorrect password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}

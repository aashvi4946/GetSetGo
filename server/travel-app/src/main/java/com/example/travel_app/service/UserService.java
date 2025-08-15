package com.example.travel_app.service;

import com.example.travel_app.dto.LoginRequest;
import com.example.travel_app.dto.RegisterRequest;
import com.example.travel_app.dto.UserResponse;
import com.example.travel_app.model.Role;
import com.example.travel_app.model.User;
import com.example.travel_app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse register(RegisterRequest req) {
        if (!StringUtils.hasText(req.getEmail()) || !StringUtils.hasText(req.getPassword()) || !StringUtils.hasText(req.getName())) {
            throw new IllegalArgumentException("Name, email and password are required");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Role role = Role.USER;
        if (StringUtils.hasText(req.getUserType())) {
            try {
                role = Role.valueOf(req.getUserType().toUpperCase());
            } catch (IllegalArgumentException ignore) { /* default USER */ }
        }

        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .userType(role)
                .build();

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public UserResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return toResponse(user);
    }

    private UserResponse toResponse(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .userType(u.getUserType())
                .createdAt(u.getCreatedAt())
                .build();
    }
}

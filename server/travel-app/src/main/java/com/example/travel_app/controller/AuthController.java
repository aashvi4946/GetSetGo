package com.example.travel_app.controller;

import com.example.travel_app.dto.LoginRequest;
import com.example.travel_app.dto.RegisterRequest;
import com.example.travel_app.dto.UserResponse;
import com.example.travel_app.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) { this.userService = userService; }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest req) {
        return userService.register(req);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest req) {
        return userService.login(req);
    }
}


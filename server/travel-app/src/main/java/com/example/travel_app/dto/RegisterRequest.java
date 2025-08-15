package com.example.travel_app.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    // Optional: allow creating admins later (lock down in Phase 3)
    private String userType; // "USER" or "ADMIN" (default USER if null/invalid)
}

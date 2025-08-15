package com.example.travel_app.dto;

import com.example.travel_app.model.Role;
import lombok.*;

import java.time.Instant;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role userType;
    private Instant createdAt;
}

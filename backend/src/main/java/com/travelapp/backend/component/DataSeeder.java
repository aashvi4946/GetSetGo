package com.travelapp.backend.component;

import com.travelapp.backend.entity.Role;
import com.travelapp.backend.entity.Tour;
import com.travelapp.backend.entity.User;
import com.travelapp.backend.repository.RoleRepository;
import com.travelapp.backend.repository.TourRepository;
import com.travelapp.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TourRepository tourRepository;

    public DataSeeder(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, TourRepository tourRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tourRepository = tourRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedUsers();
        seedTours();
    }

    private void seedUsers() {
        // Seed Admin User
        if (!userRepository.existsByEmail("admin@example.com")) {
            User adminUser = new User();
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));
            adminUser.setRoles(Collections.singleton(adminRole));
            userRepository.save(adminUser);
        }

        // Seed Regular Users
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("ROLE_USER not found"));
        List<User> users = Arrays.asList(
            new User(null, "Alice", "Smith", "alice.smith@example.com", passwordEncoder.encode("user123"), Collections.singleton(userRole)),
            new User(null, "Bob", "Johnson", "bob.johnson@example.com", passwordEncoder.encode("user123"), Collections.singleton(userRole)),
            new User(null, "Charlie", "Brown", "charlie.brown@example.com", passwordEncoder.encode("user123"), Collections.singleton(userRole))
        );

        for (User user : users) {
            if (!userRepository.existsByEmail(user.getEmail())) {
                userRepository.save(user);
            }
        }
    }

    private void seedTours() {
        List<Tour> tours = Arrays.asList(
            new Tour(null, "Parisian Dream", "Explore the romantic city of Paris.", "Paris, France", LocalDate.parse("2025-10-15"), LocalDate.parse("2025-10-22"), new BigDecimal("1999.99"), 20),
            new Tour(null, "Ancient Rome Discovery", "Walk through history in Rome.", "Rome, Italy", LocalDate.parse("2025-11-05"), LocalDate.parse("2025-11-12"), new BigDecimal("1750.00"), 25),
            new Tour(null, "Tokyo Neon Nights", "Experience the vibrant culture of Tokyo.", "Tokyo, Japan", LocalDate.parse("2026-04-10"), LocalDate.parse("2026-04-18"), new BigDecimal("2500.50"), 15),
            new Tour(null, "Himalayan Trek", "A challenging trek through the Himalayas.", "Himalayas, Nepal", LocalDate.parse("2026-05-20"), LocalDate.parse("2026-06-05"), new BigDecimal("3200.00"), 12),
            new Tour(null, "Santorini Sunset", "Relax and enjoy the stunning sunsets of Santorini.", "Santorini, Greece", LocalDate.parse("2026-07-01"), LocalDate.parse("2026-07-08"), new BigDecimal("2800.00"), 18)
        );

        for (Tour tour : tours) {
            // Assuming tour names are unique
            if (tourRepository.findByName(tour.getName()).isEmpty()) {
                tourRepository.save(tour);
            }
        }
    }
}

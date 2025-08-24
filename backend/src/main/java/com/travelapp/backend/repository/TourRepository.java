package com.travelapp.backend.repository;

import com.travelapp.backend.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {
    // Custom query methods for searching can be added here later
}
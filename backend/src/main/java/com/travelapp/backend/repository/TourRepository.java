package com.travelapp.backend.repository;

import com.travelapp.backend.entity.Tour;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TourRepository extends JpaRepository<Tour, Long> {
   // JPQL query to find the top 5 tours with the most bookings
    @Query("SELECT t FROM Tour t JOIN Booking b ON t.id = b.tour.id GROUP BY t.id ORDER BY COUNT(b.id) DESC LIMIT 5")
    List<Tour> findTop5ToursByBookings();

    // Query method to search for tours by destination and start date
    List<Tour> findByDestinationContainingIgnoreCaseAndStartDateAfter(
        @Param("destination") String destination,
        @Param("startDate") LocalDate startDate
    );

    Optional<Tour> findByName(String name);
}
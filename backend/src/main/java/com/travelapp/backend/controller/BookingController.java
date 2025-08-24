package com.travelapp.backend.controller;

import com.travelapp.backend.dto.BookingDto;
import com.travelapp.backend.dto.BookingRequestDto;
import com.travelapp.backend.entity.BookingStatus;
import com.travelapp.backend.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // USER ENDPOINTS

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        BookingDto createdBooking = bookingService.createBooking(bookingRequestDto, userEmail);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @GetMapping("/my-bookings")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BookingDto>> getCurrentUserBookings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        List<BookingDto> bookings = bookingService.getBookingsByUser(userEmail);
        return ResponseEntity.ok(bookings);
    }


    // ADMIN ENDPOINTS

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<BookingDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingDto> updateBookingStatus(@PathVariable(name = "id") long bookingId, @RequestParam BookingStatus status) {
        BookingDto updatedBooking = bookingService.updateBookingStatus(bookingId, status);
        return ResponseEntity.ok(updatedBooking);
    }
}


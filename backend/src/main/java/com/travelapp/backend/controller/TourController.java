package com.travelapp.backend.controller;

import com.travelapp.backend.dto.TourDto;
import com.travelapp.backend.service.TourService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    // PUBLIC ENDPOINTS

    @GetMapping
    public ResponseEntity<List<TourDto>> getAllTours() {
        List<TourDto> tours = tourService.getAllTours();
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDto> getTourById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(tourService.getTourById(id));
    }
    
    @GetMapping("/top-5")
    public ResponseEntity<List<TourDto>> getTop5Tours() {
    List<TourDto> tours = tourService.getTop5Tours();
    return ResponseEntity.ok(tours);
}

    @GetMapping("/search")
    public ResponseEntity<List<TourDto>> searchTours(
        @RequestParam String destination,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
    List<TourDto> tours = tourService.searchTours(destination, startDate);
    return ResponseEntity.ok(tours);
}

    // ADMIN ENDPOINTS

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TourDto> createTour(@RequestBody TourDto tourDto) {
        return new ResponseEntity<>(tourService.createTour(tourDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TourDto> updateTour(@RequestBody TourDto tourDto, @PathVariable(name = "id") long id) {
        TourDto tourResponse = tourService.updateTour(id, tourDto);
        return new ResponseEntity<>(tourResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTour(@PathVariable(name = "id") long id) {
        tourService.deleteTour(id);
        return new ResponseEntity<>("Tour entity deleted successfully.", HttpStatus.OK);
    }
}


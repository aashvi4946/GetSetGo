package com.travelapp.backend.service;

import com.travelapp.backend.dto.TourDto;

import java.time.LocalDate;
import java.util.List;

public interface TourService {
    TourDto createTour(TourDto tourDto);
    List<TourDto> getAllTours();
    TourDto getTourById(Long tourId);
    TourDto updateTour(Long tourId, TourDto tourDto);
    void deleteTour(Long tourId);
    List<TourDto> getTop5Tours();
    List<TourDto> searchTours(String destination, LocalDate startDate);
}
package com.travelapp.backend.service.implementation;

import com.travelapp.backend.dto.TourDto;
import com.travelapp.backend.entity.Tour;
import com.travelapp.backend.repository.TourRepository;
import com.travelapp.backend.service.TourService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;

    public TourServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    @CacheEvict(value = "tours", allEntries = true)
    public TourDto createTour(TourDto tourDto) {
        Tour tour = new Tour();
        BeanUtils.copyProperties(tourDto, tour);
        Tour savedTour = tourRepository.save(tour);
        BeanUtils.copyProperties(savedTour, tourDto);
        return tourDto;
    }

    @Override
    @Cacheable("tours")
    public List<TourDto> getAllTours() {
        return tourRepository.findAll().stream().map(tour -> {
            TourDto tourDto = new TourDto();
            BeanUtils.copyProperties(tour, tourDto);
            return tourDto;
        }).collect(Collectors.toList());
    }

    @Override
    public TourDto getTourById(Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + tourId));
        TourDto tourDto = new TourDto();
        BeanUtils.copyProperties(tour, tourDto);
        return tourDto;
    }

    @Override
    @CacheEvict(value = "tours", allEntries = true)
    public TourDto updateTour(Long tourId, TourDto tourDto) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + tourId));
        BeanUtils.copyProperties(tourDto, tour, "id");
        Tour updatedTour = tourRepository.save(tour);
        BeanUtils.copyProperties(updatedTour, tourDto);
        return tourDto;
    }

    @Override
    @CacheEvict(value = "tours", allEntries = true)
    public void deleteTour(Long tourId) {
        if (!tourRepository.existsById(tourId)) {
            throw new RuntimeException("Tour not found with id: " + tourId);
        }
        tourRepository.deleteById(tourId);
    }

    @Override
    @Cacheable("top-tours") // Cache the result of this expensive query
    public List<TourDto> getTop5Tours() {
    return tourRepository.findTop5ToursByBookings().stream().map(tour -> {
        TourDto tourDto = new TourDto();
        BeanUtils.copyProperties(tour, tourDto);
        return tourDto;
    }).collect(Collectors.toList());
}

    @Override
    public List<TourDto> searchTours(String destination, LocalDate startDate) {
    return tourRepository.findByDestinationContainingIgnoreCaseAndStartDateAfter(destination, startDate)
            .stream().map(tour -> {
                TourDto tourDto = new TourDto();
                BeanUtils.copyProperties(tour, tourDto);
                return tourDto;
            }).collect(Collectors.toList());
}
}

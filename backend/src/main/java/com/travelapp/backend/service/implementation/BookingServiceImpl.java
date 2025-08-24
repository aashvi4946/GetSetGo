package com.travelapp.backend.service.implementation;

import com.travelapp.backend.dto.BookingDto;
import com.travelapp.backend.dto.BookingRequestDto;
import com.travelapp.backend.entity.Booking;
import com.travelapp.backend.entity.BookingStatus;
import com.travelapp.backend.entity.Tour;
import com.travelapp.backend.entity.User;
import com.travelapp.backend.repository.BookingRepository;
import com.travelapp.backend.repository.TourRepository;
import com.travelapp.backend.repository.UserRepository;
import com.travelapp.backend.service.BookingService;
import com.travelapp.backend.service.EmailService;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TourRepository tourRepository;
    private final EmailService emailService;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, TourRepository tourRepository,EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
        this.emailService = emailService; 
        
    }

    @Override
    public BookingDto createBooking(BookingRequestDto bookingRequestDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        Tour tour = tourRepository.findById(bookingRequestDto.getTourId())
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + bookingRequestDto.getTourId()));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTour(tour);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(BookingStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);
        return mapToDto(savedBooking);
    }

    @Override
    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getBookingsByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        return bookingRepository.findByUserId(user.getId()).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    
@Override
public BookingDto updateBookingStatus(Long bookingId, BookingStatus status) {
    Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
    booking.setStatus(status);
    Booking updatedBooking = bookingRepository.save(booking);

    // Send email if the booking is approved
    if (status == BookingStatus.APPROVED) {
        emailService.sendBookingConfirmationEmail(updatedBooking);
    }

    return mapToDto(updatedBooking);
}

    

    private BookingDto mapToDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setUserId(booking.getUser().getId());
        bookingDto.setUserEmail(booking.getUser().getEmail());
        bookingDto.setTourId(booking.getTour().getId());
        bookingDto.setTourName(booking.getTour().getName());
        bookingDto.setBookingDate(booking.getBookingDate());
        bookingDto.setStatus(booking.getStatus());
        return bookingDto;
    }
}

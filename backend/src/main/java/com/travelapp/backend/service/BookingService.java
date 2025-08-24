package com.travelapp.backend.service;

import com.travelapp.backend.dto.BookingDto;
import com.travelapp.backend.dto.BookingRequestDto;
import com.travelapp.backend.entity.BookingStatus;
import java.util.List;

public interface BookingService {
    BookingDto createBooking(BookingRequestDto bookingRequestDto, String userEmail);
    List<BookingDto> getAllBookings();
    List<BookingDto> getBookingsByUser(String userEmail);
    BookingDto updateBookingStatus(Long bookingId, BookingStatus status);
}
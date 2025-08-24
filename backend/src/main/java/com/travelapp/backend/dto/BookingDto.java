package com.travelapp.backend.dto;

import com.travelapp.backend.entity.BookingStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long id;
    private Long userId;
    private String userEmail;
    private Long tourId;
    private String tourName;
    private LocalDateTime bookingDate;
    private BookingStatus status;
}
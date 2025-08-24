package com.travelapp.backend.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class BookingRequestDto implements Serializable {
    private Long tourId;
}
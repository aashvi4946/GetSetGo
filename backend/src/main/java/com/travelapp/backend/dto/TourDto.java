package com.travelapp.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TourDto {
    private Long id;
    private String name;
    private String description;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private int maxCapacity;
}

package com.travelapp.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tours")
@Getter
@Setter
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private int maxCapacity;

    public Tour(Long id, String name, String description, String destination, LocalDate startDate, LocalDate endDate, BigDecimal price, int maxCapacity) {
       this.id = id;
       this.name = name;
       this.description = description;
       this.destination = destination;
       this.startDate = startDate;
       this.endDate = endDate;
       this.price = price;
       this.maxCapacity = maxCapacity;
}
    public Tour() {}

}

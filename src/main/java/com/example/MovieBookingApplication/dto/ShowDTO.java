package com.example.MovieBookingApplication.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ShowDTO {

    private LocalDateTime showTime;
    private Double price;
    private Long movieid;
    private Long theaterid;

}

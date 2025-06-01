package com.example.MovieBookingApplication.dto;

import com.example.MovieBookingApplication.entity.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookingDTO {

    private Long id;
    private Integer numberOfSeats;
    private LocalDate bookingTime;
    private Double price;
    private BookingStatus bookingStatus;
    private List<String> seatNumbers;
    private Long userId;
    private Long showId;
}

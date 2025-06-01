package com.example.MovieBookingApplication.Controller;

import com.example.MovieBookingApplication.dto.BookingDTO;
import com.example.MovieBookingApplication.entity.Booking;
import com.example.MovieBookingApplication.entity.BookingStatus;
import com.example.MovieBookingApplication.services.BookingService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/createBooking")
    private ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) {
        return  ResponseEntity.ok(bookingService.createBooking(bookingDTO));
    }

    @GetMapping("/getUserBookings/{id}")
    private ResponseEntity<List<Booking>> getUserBookings (@PathVariable Long id){
        return ResponseEntity.ok(bookingService.getUserBookings(id));
    }

    @GetMapping("/getShowBookings/{id}")
    private ResponseEntity<List<Booking>> getShowBookings (@PathVariable Long id){
        return ResponseEntity.ok(bookingService.getShowBookings(id));
    }

    @PutMapping("/{id}/confirm")
    public  ResponseEntity<Booking> confirmBooking(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.confirmBooking(id));
    }

    @PutMapping("/{id}/cancelBooking")
    public  ResponseEntity<Booking> cancelBooking(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @GetMapping("/getBookingsByStatus/{bookingStatus}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable BookingStatus bookingStatus){
        return ResponseEntity.ok(bookingService.getBookingsByStatus(bookingStatus));
    }
}

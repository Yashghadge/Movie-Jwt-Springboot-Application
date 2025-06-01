package com.example.MovieBookingApplication.services;

import com.example.MovieBookingApplication.dto.BookingDTO;
import com.example.MovieBookingApplication.entity.Booking;
import com.example.MovieBookingApplication.entity.BookingStatus;
import com.example.MovieBookingApplication.entity.Show;
import com.example.MovieBookingApplication.entity.User;
import com.example.MovieBookingApplication.repository.BookingRepository;
import com.example.MovieBookingApplication.repository.ShowRepository;
import com.example.MovieBookingApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(BookingDTO bookingDTO) {
        Show show = showRepository.findById(bookingDTO.getShowId())
                .orElseThrow(()-> new RuntimeException("Show not found"));
        if (!isSeatsAvailable(show.getId(),bookingDTO.getNumberOfSeats())){
            throw  new RuntimeException("No seats available");
        }
        if (bookingDTO.getSeatNumbers().size() != bookingDTO.getNumberOfSeats()){
            throw  new RuntimeException("Seat numbers and number of seats must be equal");
        }
        validateDuplicateSeats(show.getId(),bookingDTO.getSeatNumbers());
        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found"));
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setNumberOfSeats(bookingDTO.getNumberOfSeats());
        booking.setSeatNumbers(bookingDTO.getSeatNumbers());
        booking.setPrice(calculateTotalAmount(show.getPrice(),bookingDTO.getNumberOfSeats()));
        booking.setBookingTime(LocalDate.now());
        booking.setBookingStatus(BookingStatus.PENDING);
        return  bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getShowBookings(Long showId) {
        return  bookingRepository.findByShowId(showId);
    }

    public Booking confirmBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Booking not found"));
        if(booking.getBookingStatus() != BookingStatus.PENDING){
            throw new RuntimeException("Booking is not in pending state");
        }
        // Payment Api Process
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Booking not found"));
        validateCancellation(booking);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    public void validateCancellation(Booking booking) {
        LocalDateTime showTime = booking.getShow().getShowTime();
        LocalDateTime deadLineTime = showTime.minusHours(2);
        if(LocalDateTime.now().isAfter(deadLineTime)){
            throw  new RuntimeException("Cannot cancel the booking");
        }
        if(booking.getBookingStatus() == BookingStatus.CANCELLED){
            throw  new RuntimeException("Already booking cancelled");
        }
    }

    public List<Booking> getBookingsByStatus(BookingStatus bookingStatus) {
        return  bookingRepository.findByBookingStatus(bookingStatus);
    }

    public boolean isSeatsAvailable(Long showId, Integer numberOfSeats) {
        Show show = showRepository.findById(showId)
                .orElseThrow(()-> new RuntimeException("Show not found"));
        int bookedSeats =  show.getBookings().stream()
                .filter(booking -> booking.getBookingStatus() != BookingStatus.CANCELLED)
                .mapToInt(Booking::getNumberOfSeats)
                .sum();
        return (show.getTheater().getTheaterCapacity() - bookedSeats) >= numberOfSeats;
    }

    public void validateDuplicateSeats(Long showId, List<String> seatNumbers) {
        Show show = showRepository.findById(showId)
                .orElseThrow(()-> new RuntimeException("Show not found"));
        Set<String> OccupiedSeats = show.getBookings().stream()
                .filter(b-> b.getBookingStatus() != BookingStatus.CANCELLED)
                .flatMap(b-> b.getSeatNumbers().stream())
                .collect(Collectors.toSet());
        List<String> duplicatesSeats  = seatNumbers.stream()
                .filter(OccupiedSeats :: contains)
                .collect(Collectors.toList());
        if(!duplicatesSeats.isEmpty()){
            throw new RuntimeException("Seats are already booked");
        }
    }

    private Double calculateTotalAmount(Double price, Integer numberOfSeats) {
        return  price * numberOfSeats;
    }
}

package com.example.MovieBookingApplication.services;

import com.example.MovieBookingApplication.dto.ShowDTO;
import com.example.MovieBookingApplication.entity.Booking;
import com.example.MovieBookingApplication.entity.Movie;
import com.example.MovieBookingApplication.entity.Show;
import com.example.MovieBookingApplication.entity.Theater;
import com.example.MovieBookingApplication.repository.MovieRepository;
import com.example.MovieBookingApplication.repository.ShowRepository;
import com.example.MovieBookingApplication.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    public Show createShow(ShowDTO showDTO) {
        Movie movie = movieRepository.findById(showDTO.getMovieid())
                .orElseThrow(()-> new RuntimeException("No Movie found for  id "+ showDTO.getMovieid()));
        Theater theater = theaterRepository.findById(showDTO.getTheaterid())
                .orElseThrow(()-> new RuntimeException("No Theater found for  id "+ showDTO.getTheaterid()));
        Show show = new Show();
        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheater(theater);
        return showRepository.save(show);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> getShowsByMovie(Long movieId) {
        Optional<List<Show>> listOfShow = showRepository.findByMovieId(movieId);
        if (listOfShow.isPresent()){
            return  listOfShow.get();
        }
        else  throw new RuntimeException("No show found for the movie " + movieId);
    }

    public List<Show> getShowsByTheater(Long theaterId) {
        Optional<List<Show>> listOfShow = showRepository.findByTheaterId(theaterId);
        if (listOfShow.isPresent()){
            return  listOfShow.get();
        }
        else  throw new RuntimeException("No show found for the movie " + theaterId);
    }

    public Show updateShow(Long id, ShowDTO showDTO) {
        Show show = showRepository.findById(id)
                .orElseThrow(
                        ()-> new RuntimeException("No Show found for the id " + id));
        Movie movie = movieRepository.findById(showDTO.getMovieid())
                .orElseThrow(()-> new RuntimeException("No Movie found for  id "+ showDTO.getMovieid()));
        Theater theater = theaterRepository.findById(showDTO.getTheaterid())
                .orElseThrow(()-> new RuntimeException("No Theater found for  id "+ showDTO.getTheaterid()));
        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheater(theater);
        return showRepository.save(show);
    }

    public void deleteShow(Long id) {
        if (!showRepository.existsById(id)){
           throw new RuntimeException("No Show available for the id "+ id);
        }
        List<Booking> bookings = showRepository.findById(id).get().getBookings();
        if (!bookings.isEmpty()){
            throw new RuntimeException("Can't delete show with existing bookings");
        }
        showRepository.deleteById(id);
    }
}

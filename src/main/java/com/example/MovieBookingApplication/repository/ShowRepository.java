package com.example.MovieBookingApplication.repository;

import com.example.MovieBookingApplication.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show,Long> {
   public Optional<List<Show>> findByMovieId(Long movieId);
   public Optional<List<Show>> findByTheaterId(Long theaterId);
}

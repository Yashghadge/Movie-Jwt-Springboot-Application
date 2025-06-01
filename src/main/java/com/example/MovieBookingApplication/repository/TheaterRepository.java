package com.example.MovieBookingApplication.repository;

import com.example.MovieBookingApplication.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater,Long> {
    public Optional<List<Theater>> findByTheaterLocation(String location);
}

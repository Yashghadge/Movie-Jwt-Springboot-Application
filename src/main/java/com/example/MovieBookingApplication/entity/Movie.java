package com.example.MovieBookingApplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer duration;
    private String genre;
    private LocalDate releaseDate;
    private String language;
    @OneToMany(mappedBy = "movie",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Show> show;
}

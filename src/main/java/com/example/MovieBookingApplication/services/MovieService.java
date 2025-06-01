package com.example.MovieBookingApplication.services;

import com.example.MovieBookingApplication.dto.MovieDTO;
import com.example.MovieBookingApplication.entity.Movie;
import com.example.MovieBookingApplication.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setName(movieDTO.getName());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDuration(movieDTO.getDuration());
        movie.setLanguage(movieDTO.getLanguage());
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesByGenre(String genre) {
        Optional<List<Movie>> ListOfMovieBox = movieRepository.findByGenre(genre);
        if(ListOfMovieBox.isPresent()){
            return ListOfMovieBox.get();
        }
        else throw new RuntimeException("No movies found for genre " + genre);
    }

    public List<Movie> getMoviesByLanguage(String language) {
        Optional<List<Movie>> ListOfMovieBox = movieRepository.findByLanguage(language);
        if(ListOfMovieBox.isPresent()){
            return ListOfMovieBox.get();
        }
        else throw new RuntimeException("No movies found for language " + language);
    }

    public List<Movie> getMoviesByTitle(String title) {
        Optional<List<Movie>> movieBox = movieRepository.findByName(title);
        if(movieBox.isPresent()){
            return movieBox.get();
        }
        else throw new RuntimeException("No movie found for title " + title);
    }

    public Movie updateMovie(long id, MovieDTO movieDTO) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No movie found for id "+ id));
        movie.setName(movieDTO.getName());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDuration(movieDTO.getDuration());
        movie.setLanguage(movieDTO.getLanguage());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}

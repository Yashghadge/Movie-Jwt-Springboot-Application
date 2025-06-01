package com.example.MovieBookingApplication.services;

import com.example.MovieBookingApplication.dto.TheaterDTO;
import com.example.MovieBookingApplication.entity.Theater;
import com.example.MovieBookingApplication.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    public Theater addTheater(TheaterDTO theaterDTO) {
        Theater theater =  new Theater();
        theater.setTheaterName(theaterDTO.getTheaterName());
        theater.setTheaterLocation(theaterDTO.getTheaterLocation());
        theater.setTheaterCapacity(theaterDTO.getTheaterCapacity());
        theater.setTheaterScreenTime(theaterDTO.getTheaterScreenTime());
        return  theaterRepository.save(theater);
    }

    public List<Theater> getTheaterByLocation(String location) {
       Optional<List<Theater>> listOfTheaterBox =  theaterRepository.findByTheaterLocation(location);
       if (listOfTheaterBox.isPresent()){
           return  listOfTheaterBox.get();
       }
       else throw  new RuntimeException("No theaters found for this location " + location );
    }

    public Theater updateTheater(Long id, TheaterDTO theaterDTO) {
        Theater theater =  theaterRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No Theater found for the id " + id));
        theater.setTheaterName(theaterDTO.getTheaterName());
        theater.setTheaterLocation(theaterDTO.getTheaterLocation());
        theater.setTheaterCapacity(theaterDTO.getTheaterCapacity());
        theater.setTheaterScreenTime(theaterDTO.getTheaterScreenTime());
        return  theaterRepository.save(theater);
    }

    public void deleteTheater(Long id) {
       theaterRepository.deleteById(id);
    }
}

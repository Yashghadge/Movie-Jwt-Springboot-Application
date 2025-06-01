package com.example.MovieBookingApplication.Controller;

import com.example.MovieBookingApplication.dto.ShowDTO;
import com.example.MovieBookingApplication.entity.Show;
import com.example.MovieBookingApplication.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/createShow")
    public ResponseEntity<Show> createShow(@RequestBody ShowDTO showDTO){
        return ResponseEntity.ok(showService.createShow(showDTO));
    }

    @GetMapping("/getAllShows")
    public ResponseEntity<List<Show>> getAllShows(){
        return  ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/getShowsByMovie/{id}")
    public ResponseEntity<List<Show>> getShowsByMovie(@RequestParam Long id){
        return  ResponseEntity.ok(showService.getShowsByMovie(id));
    }

    @GetMapping("/getShowsByTheater/{id}")
    public ResponseEntity<List<Show>> getShowsByTheater(@RequestParam Long id){
        return  ResponseEntity.ok(showService.getShowsByTheater(id));
    }

    @PostMapping("/updateShow/{id}")
    public ResponseEntity<Show> updateShow(@PathVariable Long id, @RequestBody ShowDTO showDTO){
        return ResponseEntity.ok(showService.updateShow(id,showDTO));
    }

    @PostMapping("/deleteShow/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long id){
        showService.deleteShow(id);
        return ResponseEntity.ok().build();
    }

}

package com.example.MovieBookingApplication.Controller;
import com.example.MovieBookingApplication.dto.RegisterRequestDTO;
import com.example.MovieBookingApplication.entity.User;
import com.example.MovieBookingApplication.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/registerAdminUser")
    public ResponseEntity<User> registerAdminUser(@RequestBody RegisterRequestDTO registerRequestDTO){
        return  ResponseEntity.ok(authenticationService.registerAdminUser(registerRequestDTO));
    }




}

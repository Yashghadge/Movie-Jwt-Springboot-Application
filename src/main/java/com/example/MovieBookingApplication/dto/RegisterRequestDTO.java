package com.example.MovieBookingApplication.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

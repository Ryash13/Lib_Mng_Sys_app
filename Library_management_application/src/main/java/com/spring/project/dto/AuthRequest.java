package com.spring.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class AuthRequest {

    @NotNull(message = "Please provide your registered email ID")
    @Email(message = "Please enter a valid Email ID")
    private String email;

    @NotNull(message = "Please provide your password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

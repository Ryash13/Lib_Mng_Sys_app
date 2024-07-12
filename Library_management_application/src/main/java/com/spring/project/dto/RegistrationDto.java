package com.spring.project.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;

public class RegistrationDto {

    @NotNull(message = "Firstname cannot be blank or null")
    @NotEmpty(message = "Firstname cannot be blank or null")
    private String firstname;

    @NotNull(message = "Lastname cannot be blank or null")
    @NotEmpty(message = "Lastname cannot be blank or null")
    private String lastname;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Email cannot be blank or null")
    @Email(message = "Enter a valid Email ID")
    private String email;

    @NotNull(message = "Password cannot be blank or null")
    @NotEmpty(message = "Password cannot be blank or null")
    @Size(min = 8, message = "Password must be greater than 8 characters")
    private String password;

    private Date dateOfBirth;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

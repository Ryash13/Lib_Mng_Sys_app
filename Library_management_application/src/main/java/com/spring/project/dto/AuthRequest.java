package com.spring.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class AuthRequest {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;
}

package com.spring.project.service.auth;

import com.spring.project.dto.AuthRequest;
import com.spring.project.dto.AuthResponse;
import com.spring.project.dto.RegistrationDto;
import jakarta.mail.MessagingException;

public interface AuthService {

    void registerUser(RegistrationDto user) throws MessagingException;
    void activateAccount(String token) throws MessagingException;
    AuthResponse login(AuthRequest authRequest);
}

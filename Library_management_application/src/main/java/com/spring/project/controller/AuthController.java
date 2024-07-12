package com.spring.project.controller;

import com.spring.project.dto.AuthRequest;
import com.spring.project.dto.AuthResponse;
import com.spring.project.dto.RegistrationDto;
import com.spring.project.service.auth.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegistrationDto register, HttpServletRequest request) throws MessagingException {
        log(request);
        authService.registerUser(register);
        return ResponseEntity.ok().build();
    }

    @PostMapping("activate-account")
    public ResponseEntity<?> activateAccount(@RequestParam String token, HttpServletRequest request) throws MessagingException {
        log(request);
        authService.activateAccount(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest, HttpServletRequest request) {
        log(request);
        return new ResponseEntity<>(authService.login(authRequest), HttpStatus.OK);
    }

    private void log(HttpServletRequest request) {
        log.info("Executing API - {} from File - {}",request.getServletPath(), this.getClass());
    }
}

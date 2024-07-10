package com.spring.project.exception;

import com.spring.project.dto.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
        var response = new ExceptionResponse();
        response.setError(exception.getMessage());
        response.setErrorStatusCode(exception.getStatus().value());
        return new ResponseEntity<>(response, exception.getStatus());
    }
}

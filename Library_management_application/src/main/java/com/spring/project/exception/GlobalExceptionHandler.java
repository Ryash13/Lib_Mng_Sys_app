package com.spring.project.exception;

import com.spring.project.dto.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.spring.project.constant.ErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleException(CustomException exception) {
        var response = new ExceptionResponse();
        response.setError(exception.getMessage());
        response.setErrorStatusCode(exception.getStatus().value());
        response.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(response, exception.getStatus());
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<?> handleException(LockedException exception) {
        var response = new ExceptionResponse();
        response.setCustomErrorCode(ACCOUNT_LOCKED.getCode());
        response.setError(ACCOUNT_LOCKED.getDescription());
        response.setErrorStatusCode(ACCOUNT_LOCKED.getHttpStatus().value());
        response.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(response, ACCOUNT_LOCKED.getHttpStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleException(BadCredentialsException exception) {
        var response = new ExceptionResponse();
        response.setCustomErrorCode(BAD_CREDENTIALS.getCode());
        response.setError(BAD_CREDENTIALS.getDescription());
        response.setErrorStatusCode(BAD_CREDENTIALS.getHttpStatus().value());
        response.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(response, BAD_CREDENTIALS.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exception) {
        Set<String> errors = new HashSet<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach(err -> {
                    var errorMessage = err.getDefaultMessage();
                    errors.add(errorMessage);
                });
        var response = new ExceptionResponse();
        response.setCustomErrorCode(VALIDATION_REQUEST_ERROR.getCode());
        response.setTimestamp(LocalDateTime.now());
        response.setErrors(errors.stream().toList());
        response.setErrorStatusCode(VALIDATION_REQUEST_ERROR.getHttpStatus().value());
        return new ResponseEntity<>(response, VALIDATION_REQUEST_ERROR.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
        var response = new ExceptionResponse();
        response.setError(exception.getMessage());
        response.setTimestamp(LocalDateTime.now());
        response.setErrorStatusCode(INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
    }
}

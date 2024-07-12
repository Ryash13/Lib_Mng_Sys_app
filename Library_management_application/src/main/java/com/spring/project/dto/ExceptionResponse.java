package com.spring.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

    private int customErrorCode;
    private int errorStatusCode;
    private String error;
    private List<String> errors;
    private LocalDateTime timestamp;
    private HttpStatus httpStatus;

    public ExceptionResponse() {}

    public ExceptionResponse(int customErrorCode, int errorStatusCode, String error, List<String> errors, LocalDateTime timestamp, HttpStatus httpStatus) {
        this.customErrorCode = customErrorCode;
        this.errorStatusCode = errorStatusCode;
        this.error = error;
        this.errors = errors;
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
    }

    public int getCustomErrorCode() {
        return customErrorCode;
    }

    public void setCustomErrorCode(int customErrorCode) {
        this.customErrorCode = customErrorCode;
    }

    public int getErrorStatusCode() {
        return errorStatusCode;
    }

    public void setErrorStatusCode(int errorStatusCode) {
        this.errorStatusCode = errorStatusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}

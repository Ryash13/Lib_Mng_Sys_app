package com.spring.project.dto;

import java.util.Map;

public class ExceptionResponse {

    private int errorStatusCode;
    private String error;

    public ExceptionResponse() {}

    public ExceptionResponse(int errorStatusCode, String error) {
        this.errorStatusCode = errorStatusCode;
        this.error = error;
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
}

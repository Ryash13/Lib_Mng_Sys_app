package com.spring.project.constant;

import org.springframework.http.HttpStatus;

import static com.spring.project.constant.Constants.*;
import static org.springframework.http.HttpStatus.*;

public enum ErrorCodes {
    INCORRECT_CURRENT_PASSWORD(301,INVALID_CURRENT_PASSWORD, BAD_REQUEST),
    NEW_PASSWORD_MISMATCH(302, PASSWORD_MISMATCH, BAD_REQUEST),
    ACCOUNT_LOCKED(303, LOCKED_ACCOUNT, FORBIDDEN),
    ACCOUNT_DISABLED(304, DISABLED_ACCOUNT,FORBIDDEN),
    BAD_CREDENTIALS(305,INVALID_CREDENTIALS, UNAUTHORIZED),
    VALIDATION_REQUEST_ERROR(306,INVALID_REQUEST_PASSED,BAD_REQUEST)
    ;

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    ErrorCodes(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

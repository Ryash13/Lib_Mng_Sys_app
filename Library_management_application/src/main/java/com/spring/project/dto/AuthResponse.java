package com.spring.project.dto;

public record AuthResponse(String token, String displayName) {

    @Override
    public String token() {
        return token;
    }

    @Override
    public String displayName() {
        return displayName;
    }
}

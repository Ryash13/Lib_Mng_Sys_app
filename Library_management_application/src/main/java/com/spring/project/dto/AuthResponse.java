package com.spring.project.dto;

public record AuthResponse(String token, String displayName, String uuid) {

    @Override
    public String token() {
        return token;
    }

    @Override
    public String displayName() {
        return displayName;
    }

    @Override
    public String uuid() {
        return uuid;
    }
}

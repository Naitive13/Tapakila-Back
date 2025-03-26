package com.tapakila.Tapakila_Backend.Entity.UserVariant;

import lombok.Getter;

@Getter
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}

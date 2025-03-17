package com.tapakila.Tapakila_Backend.Entity;


import java.util.UUID;

public class UserDetailResponse {
    private final String userName;
    private final String userEmail;
    private final UUID userId;

    public UserDetailResponse(String userName, String userEmail, UUID userId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userId = userId;
    }
}

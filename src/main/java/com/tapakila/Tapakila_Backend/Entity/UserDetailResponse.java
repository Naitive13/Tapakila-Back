package com.tapakila.Tapakila_Backend.Entity;


import lombok.Getter;

@Getter
public class UserDetailResponse {
    private final String userName;
    private final String userEmail;
    private final String userId;

    public UserDetailResponse(String userName, String userEmail, String userId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userId = userId;
    }
}

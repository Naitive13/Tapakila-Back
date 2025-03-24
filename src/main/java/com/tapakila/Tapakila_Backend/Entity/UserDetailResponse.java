package com.tapakila.Tapakila_Backend.Entity;


import lombok.Getter;

import java.time.Instant;

@Getter
public class UserDetailResponse {
    private final String userName;
    private final String userEmail;
    private final String userId;
    private Instant creationDate;

    public UserDetailResponse(String userName, String userEmail, String userId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userId = userId;
    }

    public UserDetailResponse(String userName, String userEmail, String userId, Instant creationDate) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userId = userId;
        this.creationDate = creationDate;
    }
}

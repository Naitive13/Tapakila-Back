package com.tapakila.Tapakila_Backend.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class User {
    private UUID userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userType;
    private String creationDate;

    public User(UUID userId, String userName, String userEmail, String userPassword, String userType, String creationDate) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userType = userType;
        this.creationDate = creationDate;
    }
}

package com.tapakila.Tapakila_Backend.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class User {
    private UUID userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private UserType userType;
    private String creationDate;

}

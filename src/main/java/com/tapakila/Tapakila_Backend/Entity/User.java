package com.tapakila.Tapakila_Backend.Entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class User {
    private UUID userId;
    private String userName;
    private String email;
    private String password;
    private UserType type;
    private Instant creationDate;

}

package com.tapakila.Tapakila_Backend.Entity;

import com.tapakila.Tapakila_Backend.Entity.Enum.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
@Getter
@Setter
@ToString
public class User {
    private String userId;
    private String userName;
    private String email;
    private String password;
    private UserType type;
    private Instant creationDate;

}

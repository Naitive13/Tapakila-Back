package com.tapakila.Tapakila_Backend.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String userName;
    private String email;
    private String password;
    private UserType type;
}

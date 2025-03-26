package com.tapakila.Tapakila_Backend.Entity.UserVariant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;
}

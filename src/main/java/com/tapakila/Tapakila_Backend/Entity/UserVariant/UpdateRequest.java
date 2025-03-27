package com.tapakila.Tapakila_Backend.Entity.UserVariant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest {
    String userName, email, userId, userPassword;
}

package com.tapakila.Tapakila_Backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/me")
    public String hello(){
        return "hello";
    }
}

package com.tapakila.Tapakila_Backend.Controller;

import com.tapakila.Tapakila_Backend.Entity.User;
import com.tapakila.Tapakila_Backend.Entity.UserDetailResponse;
import com.tapakila.Tapakila_Backend.Repository.UserDAOImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    UserDAOImpl userDAO;

    @RequestMapping("/me")
    public ResponseEntity<UserDetailResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userDAO.getUserByEmail(email);
        UserDetailResponse userDetail = new UserDetailResponse(
                user.getUserName(),
                user.getUserEmail(),
                user.getUserId()
        );

        return ResponseEntity.ok(userDetail);
    }
}

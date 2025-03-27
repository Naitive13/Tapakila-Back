package com.tapakila.Tapakila_Backend.Controller;

import com.tapakila.Tapakila_Backend.Entity.User;
import com.tapakila.Tapakila_Backend.Entity.UserVariant.UpdateRequest;
import com.tapakila.Tapakila_Backend.Entity.UserVariant.UserDetailResponse;
import com.tapakila.Tapakila_Backend.Repository.Datasource;
import com.tapakila.Tapakila_Backend.Repository.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
//@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/user")
public class UserController {
    private Logger logger   = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserDAOImpl userDAO;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAll(){
        List<User> userList = userDAO.getAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userDAO.getUserByEmail(email);
        UserDetailResponse userDetail = new UserDetailResponse(
                user.getUserName(),
                user.getEmail(),
                user.getUserId(),
                user.getCreationDate()
        );

        return ResponseEntity.ok(userDetail);
    }
    @RequestMapping("/{userID}")
    public ResponseEntity<UserDetailResponse> getUserByID(@PathVariable String userID) {
        User user = userDAO.getUserByUUID(userID);
        UserDetailResponse userDetail = new UserDetailResponse(
                user.getUserName(),
                user.getEmail(),
                user.getUserId(),
                user.getCreationDate()
        );

        return ResponseEntity.ok(userDetail);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String>  deleteUser(@PathVariable String userId){
        try {
            userDAO.delete(userId);
            return ResponseEntity.ok("user : " + userId + " has been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("user : " + userId + " deletion has failed." + e.getMessage());
        }
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateUserInfo(@RequestBody UpdateRequest updateRequest){
        try {
            userDAO.updateUser(updateRequest);
            return ResponseEntity.ok(userDAO.getUserByEmail(updateRequest.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userDAO.getUserByEmail(updateRequest.getEmail()));
        }
    }
}

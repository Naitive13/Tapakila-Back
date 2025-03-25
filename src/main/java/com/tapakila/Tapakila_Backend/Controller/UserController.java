package com.tapakila.Tapakila_Backend.Controller;

import com.tapakila.Tapakila_Backend.Entity.User;
import com.tapakila.Tapakila_Backend.Entity.UserDetailResponse;
import com.tapakila.Tapakila_Backend.Repository.Datasource;
import com.tapakila.Tapakila_Backend.Repository.UserDAOImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/user")
public class UserController {
    Datasource datasource = new Datasource();
    UserDAOImpl userDAO = new UserDAOImpl(datasource);

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
                user.getUserId()
        );

        return ResponseEntity.ok(userDetail);
    }

    @GetMapping("/{userID}")
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
}

package com.tapakila.Tapakila_Backend.Controller;

import com.tapakila.Tapakila_Backend.Entity.Enum.UserType;
import com.tapakila.Tapakila_Backend.Entity.Reservation;
import com.tapakila.Tapakila_Backend.Entity.User;
import com.tapakila.Tapakila_Backend.Entity.UserVariant.SignupRequest;
import com.tapakila.Tapakila_Backend.Entity.UserVariant.UpdateRequest;
import com.tapakila.Tapakila_Backend.Entity.UserVariant.UserDetailResponse;
import com.tapakila.Tapakila_Backend.Repository.ReservationDAOImpl;
import com.tapakila.Tapakila_Backend.Repository.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDAOImpl userDAO;
    @Autowired
    ReservationDAOImpl reservationDAO;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll(@RequestParam(required = false, defaultValue = "10") int pageSize,
                                             @RequestParam(required = false, defaultValue = "1") int pageNumber
    ) {
        List<User> userList = userDAO.getAll(pageSize, pageNumber);
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

    @GetMapping("/me/reservation")
    public ResponseEntity<List<Reservation>> getCurrentUserReservation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userDAO.getUserByEmail(email);
        return ResponseEntity.ok(reservationDAO.getByUserId(user.getUserId()));
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
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        try {
            userDAO.delete(userId);
            return ResponseEntity.ok("user : " + userId + " has been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("user : " + userId + " deletion has failed." + e.getMessage());
        }
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateUserInfo(@RequestBody UpdateRequest updateRequest) {
        try {
            userDAO.updateUser(updateRequest);
            return ResponseEntity.ok(userDAO.getUserByEmail(updateRequest.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userDAO.getUserByEmail(updateRequest.getEmail()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserInfo(@PathVariable String id,@RequestBody UserType type) {
        try {
            userDAO.updateUserType(id, type);
            return ResponseEntity.ok(userDAO.getUserByUUID(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userDAO.getUserByUUID(id));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody SignupRequest newUser) {
        try {
            // Check if the email is already registered
            if (userDAO.existsByUserEmail(newUser.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered");
            }

            // Create a new user
            User user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setUserName(newUser.getUserName());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword()); // Password will be hashed in the repository
            user.setType(UserType.valueOf(String.valueOf(newUser.getType())));
            user.setCreationDate(Instant.now());


            // Save the user to the database
            userDAO.save(user);

            // Return a success response
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            // Log the exception for debugging
            System.out.println(e);

            // Return a detailed error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }
}

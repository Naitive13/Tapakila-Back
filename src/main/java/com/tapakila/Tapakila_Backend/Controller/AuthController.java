package com.tapakila.Tapakila_Backend.Controller;

import com.tapakila.Tapakila_Backend.Entity.*;
import com.tapakila.Tapakila_Backend.Repository.UserDAOImpl;
import com.tapakila.Tapakila_Backend.Services.CustomUserDetailsService;
import com.tapakila.Tapakila_Backend.Security.JwUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDAOImpl userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        try {
            // Check if the email is already registered
            if (userRepository.existsByUserEmail(signupRequest.getUserEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered");
            }

            // Create a new user
            User user = new User();
            user.setUserId(UUID.randomUUID());
            user.setUserName(signupRequest.getUserName());
            user.setUserEmail(signupRequest.getUserEmail());
            user.setUserPassword(signupRequest.getUserPassword()); // Password will be hashed in the repository
            user.setUserType(UserType.valueOf(String.valueOf(signupRequest.getUserType())));

            // Save the user to the database
            userRepository.save(user);

            // Return a success response
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            // Log the exception for debugging
            System.out.println(e);

            // Return a detailed error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            // Generate a JWT token
            final String token = jwtUtil.generateToken(userDetails);

            // Return the token in the response
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Login failed"  +  e.getMessage()) );
        }
    }
}
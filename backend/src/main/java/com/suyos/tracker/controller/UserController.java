package com.suyos.tracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suyos.tracker.dto.AuthenticationResponseDTO;
import com.suyos.tracker.dto.UserLoginDTO;
import com.suyos.tracker.dto.UserProfileDTO;
import com.suyos.tracker.dto.UserRegistrationDTO;
import com.suyos.tracker.dto.UserUpdateDTO;
import com.suyos.tracker.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for user management operations.
 * 
 * Provides endpoints for user authentication, registration, and profile management.
 * Handles HTTP requests and delegates business logic to the UserService layer.
 * 
 * All endpoints return appropriate HTTP status codes and error messages
 * for client applications to handle authentication and user management flows.
 * 
 * @author Joel Salazar
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    /** Service layer for user business logic */
    private final UserService userService;

    /**
     * Registers a new user account.
     * 
     * Creates a new user with the provided registration information.
     * Validates input data and returns the created user's profile.
     * 
     * @param registrationDTO the user registration data
     * @return ResponseEntity containing the created user's profile or error message
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        try {
            UserProfileDTO userProfile = userService.registerUser(registrationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userProfile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Registration failed: " + e.getMessage());
        }
    }

    /**
     * Authenticates a user login attempt and returns JWT token.
     * 
     * Validates user credentials and returns JWT token with user profile on success.
     * Handles account locking and failed login attempts.
     * 
     * @param loginDTO the user login credentials
     * @return ResponseEntity containing JWT token and user profile or error message
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO loginDTO) {
        try {
            AuthenticationResponseDTO authResponse = userService.authenticateUser(loginDTO);
            return ResponseEntity.ok(authResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Authentication failed: " + e.getMessage());
        }
    }

    /**
     * Retrieves a user's profile information.
     * 
     * Returns the complete profile data for the specified user.
     * Used for displaying user information in the application.
     * 
     * @param userId the user's unique identifier
     * @return ResponseEntity containing the user's profile or error message
     */
    @GetMapping("/{userId}/profile")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        try {
            UserProfileDTO userProfile = userService.getUserProfile(userId);
            return ResponseEntity.ok(userProfile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User not found: " + e.getMessage());
        }
    }

    /**
     * Updates a user's profile information.
     * 
     * Allows users to modify their profile data excluding sensitive fields
     * like email and password. Validates input and returns updated profile.
     * 
     * @param userId the user's unique identifier
     * @param updateDTO the updated profile information
     * @return ResponseEntity containing the updated user's profile or error message
     */
    @PutMapping("/{userId}/profile")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long userId, 
                                             @Valid @RequestBody UserUpdateDTO updateDTO) {
        try {
            UserProfileDTO updatedProfile = userService.updateUserProfile(userId, updateDTO);
            return ResponseEntity.ok(updatedProfile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Profile update failed: " + e.getMessage());
        }
    }
}
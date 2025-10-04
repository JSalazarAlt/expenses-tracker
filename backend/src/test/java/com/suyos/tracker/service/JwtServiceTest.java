package com.suyos.tracker.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit tests for JwtService.
 * 
 * @author Joel Salazar
 * @since 1.0
 */
@DisplayName("JwtService Unit Tests")
class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // Set test values using reflection to avoid dependency on application.properties
        ReflectionTestUtils.setField(jwtService, "secretKey", "mySecretKey123456789012345678901234567890123456789012345678901234567890");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000L);

        userDetails = User.builder()
                .username("test@example.com")
                .password("password")
                .authorities(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("Should generate JWT token")
    void generateToken_ValidUserDetails_ReturnsToken() {
        // When
        String token = jwtService.generateToken(userDetails);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));  // JWT format has dots
    }

    @Test
    @DisplayName("Should extract username from token")
    void extractUsername_ValidToken_ReturnsUsername() {
        // Given
        String token = jwtService.generateToken(userDetails);

        // When
        String username = jwtService.extractUsername(token);

        // Then
        assertEquals("test@example.com", username);
    }

    @Test
    @DisplayName("Should validate token successfully")
    void isTokenValid_ValidToken_ReturnsTrue() {
        // Given
        String token = jwtService.generateToken(userDetails);

        // When
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should invalidate token for different user")
    void isTokenValid_DifferentUser_ReturnsFalse() {
        // Given
        String token = jwtService.generateToken(userDetails);
        UserDetails differentUser = User.builder()
                .username("different@example.com")
                .password("password")
                .authorities(new ArrayList<>())
                .build();

        // When
        boolean isValid = jwtService.isTokenValid(token, differentUser);

        // Then
        assertFalse(isValid);
    }



    @Test
    @DisplayName("Should get expiration time")
    void getExpirationTime_ReturnsConfiguredTime() {
        // When
        Long expirationTime = jwtService.getExpirationTime();

        // Then
        assertEquals(86400000L, expirationTime);
    }

    @Test
    @DisplayName("Should throw exception for invalid token")
    void extractUsername_InvalidToken_ThrowsException() {
        // Given
        String invalidToken = "invalid.token.here";

        // When & Then
        assertThrows(Exception.class, () -> jwtService.extractUsername(invalidToken));
    }

    @Test
    @DisplayName("Should throw exception for null token")
    void extractUsername_NullToken_ThrowsException() {
        // When & Then
        assertThrows(Exception.class, () -> jwtService.extractUsername(null));
    }
}
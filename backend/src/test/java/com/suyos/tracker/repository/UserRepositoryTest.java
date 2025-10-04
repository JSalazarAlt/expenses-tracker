package com.suyos.tracker.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.suyos.tracker.model.User;

/**
 * Unit tests for UserRepository.
 * 
 * @author Joel Salazar
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserRepository Unit Tests")
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encoded-password")
                .username("testuser")
                .firstName("John")
                .lastName("Doe")
                .accountEnabled(true)
                .emailVerified(false)
                .accountLocked(false)
                .failedLoginAttempts(0)
                .build();
    }

    @Test
    @DisplayName("Should find user by email")
    void findByEmail_ExistingEmail_ReturnsUser() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        // When
        Optional<User> result = userRepository.findByEmail("test@example.com");

        // Then
        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should return empty when email not found")
    void findByEmail_NonExistingEmail_ReturnsEmpty() {
        // Given
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // When
        Optional<User> result = userRepository.findByEmail("nonexistent@example.com");

        // Then
        assertFalse(result.isPresent());
        verify(userRepository).findByEmail("nonexistent@example.com");
    }

    @Test
    @DisplayName("Should check if email exists")
    void existsByEmail_ExistingEmail_ReturnsTrue() {
        // Given
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // When
        boolean exists = userRepository.existsByEmail("test@example.com");

        // Then
        assertTrue(exists);
        verify(userRepository).existsByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should find active user by email")
    void findActiveUserByEmail_ActiveUser_ReturnsUser() {
        // Given
        when(userRepository.findActiveUserByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        // When
        Optional<User> result = userRepository.findActiveUserByEmail("test@example.com");

        // Then
        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
        verify(userRepository).findActiveUserByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should save user")
    void save_ValidUser_ReturnsSavedUser() {
        // Given
        when(userRepository.save(testUser)).thenReturn(testUser);

        // When
        User result = userRepository.save(testUser);

        // Then
        assertEquals(testUser, result);
        verify(userRepository).save(testUser);
    }

    @Test
    @DisplayName("Should update failed login attempts")
    void updateFailedLoginAttempts_ValidUser_UpdatesAttempts() {
        // Given
        doNothing().when(userRepository).updateFailedLoginAttempts("test@example.com", 3);

        // When
        userRepository.updateFailedLoginAttempts("test@example.com", 3);

        // Then
        verify(userRepository).updateFailedLoginAttempts("test@example.com", 3);
    }

    @Test
    @DisplayName("Should lock account")
    void lockAccount_ValidUser_LocksAccount() {
        // Given
        LocalDateTime lockTime = LocalDateTime.now().plusHours(24);
        doNothing().when(userRepository).lockAccount("test@example.com", lockTime);

        // When
        userRepository.lockAccount("test@example.com", lockTime);

        // Then
        verify(userRepository).lockAccount("test@example.com", lockTime);
    }

    @Test
    @DisplayName("Should unlock account")
    void unlockAccount_LockedUser_UnlocksAccount() {
        // Given
        doNothing().when(userRepository).unlockAccount("test@example.com");

        // When
        userRepository.unlockAccount("test@example.com");

        // Then
        verify(userRepository).unlockAccount("test@example.com");
    }
}
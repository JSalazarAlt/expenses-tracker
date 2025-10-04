package com.suyos.tracker.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.suyos.tracker.dto.AuthenticationResponseDTO;
import com.suyos.tracker.dto.UserLoginDTO;
import com.suyos.tracker.dto.UserProfileDTO;
import com.suyos.tracker.dto.UserRegistrationDTO;
import com.suyos.tracker.mapper.UserMapper;
import com.suyos.tracker.model.User;
import com.suyos.tracker.repository.UserRepository;

/**
 * Unit tests for UserService.
 * 
 * @author Joel Salazar
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserRegistrationDTO registrationDTO;
    private UserLoginDTO loginDTO;
    private UserProfileDTO profileDTO;

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

        registrationDTO = UserRegistrationDTO.builder()
                .email("test@example.com")
                .password("Password123!")
                .username("testuser")
                .firstName("John")
                .lastName("Doe")
                .termsAccepted(true)
                .privacyPolicyAccepted(true)
                .build();

        loginDTO = UserLoginDTO.builder()
                .email("test@example.com")
                .password("Password123!")
                .build();

        profileDTO = UserProfileDTO.builder()
                .id(1L)
                .email("test@example.com")
                .username("testuser")
                .firstName("John")
                .lastName("Doe")
                .build();
    }

    @Test
    @DisplayName("Should register user successfully")
    void registerUser_ValidData_ReturnsUserProfile() {
        // Given
        when(userRepository.existsByEmail(registrationDTO.getEmail())).thenReturn(false);
        when(userMapper.toEntity(registrationDTO)).thenReturn(testUser);
        when(passwordEncoder.encode(registrationDTO.getPassword())).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toProfileDTO(testUser)).thenReturn(profileDTO);

        // When
        UserProfileDTO result = userService.registerUser(registrationDTO);

        // Then
        assertNotNull(result);
        assertEquals(profileDTO.getEmail(), result.getEmail());
        verify(userRepository).existsByEmail(registrationDTO.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void registerUser_ExistingEmail_ThrowsException() {
        // Given
        when(userRepository.existsByEmail(registrationDTO.getEmail())).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.registerUser(registrationDTO));

        assertEquals("Email already registered", exception.getMessage());
        verify(userRepository).existsByEmail(registrationDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should authenticate user successfully")
    void authenticateUser_ValidCredentials_ReturnsAuthResponse() {
        // Given
        when(userRepository.findActiveUserByEmail(loginDTO.getEmail())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(loginDTO.getPassword(), testUser.getPassword())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(jwtService.generateToken(any())).thenReturn("jwt-token");
        when(jwtService.getExpirationTime()).thenReturn(86400000L);
        when(userMapper.toProfileDTO(testUser)).thenReturn(profileDTO);

        // When
        AuthenticationResponseDTO result = userService.authenticateUser(loginDTO);

        // Then
        assertNotNull(result);
        assertEquals("jwt-token", result.getAccessToken());
        assertEquals(profileDTO, result.getUser());
        verify(userRepository).findActiveUserByEmail(loginDTO.getEmail());
        verify(passwordEncoder).matches(loginDTO.getPassword(), testUser.getPassword());
    }

    @Test
    @DisplayName("Should throw exception for invalid credentials")
    void authenticateUser_InvalidCredentials_ThrowsException() {
        // Given
        when(userRepository.findActiveUserByEmail(loginDTO.getEmail())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(loginDTO.getPassword(), testUser.getPassword())).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.authenticateUser(loginDTO));

        assertEquals("Invalid email or password", exception.getMessage());
        verify(userRepository).findActiveUserByEmail(loginDTO.getEmail());
        verify(passwordEncoder).matches(loginDTO.getPassword(), testUser.getPassword());
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void authenticateUser_UserNotFound_ThrowsException() {
        // Given
        when(userRepository.findActiveUserByEmail(loginDTO.getEmail())).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.authenticateUser(loginDTO));

        assertEquals("Invalid email or password", exception.getMessage());
        verify(userRepository).findActiveUserByEmail(loginDTO.getEmail());
        verify(passwordEncoder, never()).matches(any(), any());
    }

    @Test
    @DisplayName("Should get current user ID successfully")
    void getCurrentUserId_AuthenticatedUser_ReturnsUserId() {
        // Given
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        // When
        Long result = userService.getCurrentUserId();

        // Then
        assertEquals(1L, result);
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should throw exception when current user not found")
    void getCurrentUserId_UserNotFound_ThrowsException() {
        // Given
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.getCurrentUserId());

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findByEmail("test@example.com");
    }
}
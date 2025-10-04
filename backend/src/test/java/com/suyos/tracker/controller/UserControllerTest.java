package com.suyos.tracker.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suyos.tracker.dto.AuthenticationResponseDTO;
import com.suyos.tracker.dto.UserLoginDTO;
import com.suyos.tracker.dto.UserProfileDTO;
import com.suyos.tracker.dto.UserRegistrationDTO;
import com.suyos.tracker.service.UserService;

/**
 * Unit tests for UserController.
 * 
 * @author Joel Salazar
 * @since 1.0
 */
@WebMvcTest(UserController.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("UserController Unit Tests")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRegistrationDTO registrationDTO;
    private UserLoginDTO loginDTO;
    private UserProfileDTO profileDTO;
    private AuthenticationResponseDTO authResponseDTO;

    @BeforeEach
    void setUp() {
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

        authResponseDTO = AuthenticationResponseDTO.builder()
                .accessToken("jwt-token")
                .expiresIn(86400000L)
                .user(profileDTO)
                .build();
    }

    @Test
    @DisplayName("Should register user successfully")
    void registerUser_ValidData_ReturnsCreated() throws Exception {
        when(userService.registerUser(any(UserRegistrationDTO.class))).thenReturn(profileDTO);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(userService).registerUser(any(UserRegistrationDTO.class));
    }

    @Test
    @DisplayName("Should return bad request for invalid registration data")
    void registerUser_InvalidData_ReturnsBadRequest() throws Exception {
        when(userService.registerUser(any(UserRegistrationDTO.class)))
                .thenThrow(new RuntimeException("Email already registered"));

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isBadRequest());

        verify(userService).registerUser(any(UserRegistrationDTO.class));
    }

    @Test
    @DisplayName("Should login user successfully")
    void loginUser_ValidCredentials_ReturnsToken() throws Exception {
        when(userService.authenticateUser(any(UserLoginDTO.class))).thenReturn(authResponseDTO);

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("jwt-token"))
                .andExpect(jsonPath("$.user.email").value("test@example.com"));

        verify(userService).authenticateUser(any(UserLoginDTO.class));
    }

    @Test
    @DisplayName("Should return unauthorized for invalid credentials")
    void loginUser_InvalidCredentials_ReturnsUnauthorized() throws Exception {
        when(userService.authenticateUser(any(UserLoginDTO.class)))
                .thenThrow(new RuntimeException("Invalid email or password"));

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());

        verify(userService).authenticateUser(any(UserLoginDTO.class));
    }

    @Test
    @DisplayName("Should get user profile successfully")
    void getUserProfile_ExistingUser_ReturnsProfile() throws Exception {
        when(userService.getUserProfile(1L)).thenReturn(profileDTO);

        mockMvc.perform(get("/api/users/1/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(userService).getUserProfile(1L);
    }

    @Test
    @DisplayName("Should return not found for non-existing user")
    void getUserProfile_NonExistingUser_ReturnsNotFound() throws Exception {
        when(userService.getUserProfile(999L))
                .thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(get("/api/users/999/profile"))
                .andExpect(status().isNotFound());

        verify(userService).getUserProfile(999L);
    }
}
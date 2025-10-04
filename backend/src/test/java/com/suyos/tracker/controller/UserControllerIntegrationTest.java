package com.suyos.tracker.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.suyos.tracker.dto.UserLoginDTO;
import com.suyos.tracker.dto.UserRegistrationDTO;
import com.suyos.tracker.repository.UserRepository;

/**
 * Integration tests for UserController.
 * 
 * @author Joel Salazar
 * @since 1.0
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
@DisplayName("UserController Integration Tests")
class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Autowired
    private UserRepository userRepository;

    private UserRegistrationDTO registrationDTO;
    private UserLoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/users";
        userRepository.deleteAll();

        registrationDTO = UserRegistrationDTO.builder()
                .email("integration@example.com")
                .password("Password123!")
                .username("integrationuser")
                .firstName("Integration")
                .lastName("Test")
                .termsAccepted(true)
                .privacyPolicyAccepted(true)
                .build();

        loginDTO = UserLoginDTO.builder()
                .email("integration@example.com")
                .password("Password123!")
                .build();
    }

    @Test
    @DisplayName("Should complete full registration and login flow")
    void fullAuthenticationFlow_ValidData_Success() {
        // Register user
        ResponseEntity<String> registerResponse = restTemplate.postForEntity(
                baseUrl + "/register", registrationDTO, String.class);
        assertEquals(HttpStatus.CREATED.value(), registerResponse.getStatusCode().value());

        // Login with registered user
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(
                baseUrl + "/login", loginDTO, String.class);
        assertEquals(HttpStatus.OK.value(), loginResponse.getStatusCode().value());
        String responseBody = loginResponse.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.contains("accessToken"));
    }

    @Test
    @DisplayName("Should prevent duplicate email registration")
    void registerUser_DuplicateEmail_ReturnsBadRequest() {
        // Register first user
        ResponseEntity<String> firstResponse = restTemplate.postForEntity(
                baseUrl + "/register", registrationDTO, String.class);
        assertEquals(HttpStatus.CREATED.value(), firstResponse.getStatusCode().value());

        // Try to register with same email
        UserRegistrationDTO duplicateDTO = UserRegistrationDTO.builder()
                .email("integration@example.com")  // Same email
                .password("DifferentPassword123!")
                .username("differentuser")
                .firstName("Different")
                .lastName("User")
                .termsAccepted(true)
                .privacyPolicyAccepted(true)
                .build();

        ResponseEntity<String> duplicateResponse = restTemplate.postForEntity(
                baseUrl + "/register", duplicateDTO, String.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), duplicateResponse.getStatusCode().value());
    }

    @Test
    @DisplayName("Should reject login with wrong password")
    void loginUser_WrongPassword_ReturnsUnauthorized() {
        // Register user first
        restTemplate.postForEntity(baseUrl + "/register", registrationDTO, String.class);

        // Try login with wrong password
        UserLoginDTO wrongPasswordDTO = UserLoginDTO.builder()
                .email("integration@example.com")
                .password("WrongPassword123!")
                .build();

        ResponseEntity<String> loginResponse = restTemplate.postForEntity(
                baseUrl + "/login", wrongPasswordDTO, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), loginResponse.getStatusCode().value());
    }

    @Test
    @DisplayName("Should reject login for non-existent user")
    void loginUser_NonExistentUser_ReturnsUnauthorized() {
        UserLoginDTO nonExistentUserDTO = UserLoginDTO.builder()
                .email("nonexistent@example.com")
                .password("Password123!")
                .build();

        ResponseEntity<String> loginResponse = restTemplate.postForEntity(
                baseUrl + "/login", nonExistentUserDTO, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), loginResponse.getStatusCode().value());
    }

    @Test
    @DisplayName("Should validate registration input")
    void registerUser_InvalidInput_ReturnsBadRequest() {
        UserRegistrationDTO invalidDTO = UserRegistrationDTO.builder()
                .email("invalid-email")  // Invalid email format
                .password("weak")        // Weak password
                .username("")            // Empty username
                .firstName("")           // Empty first name
                .lastName("")            // Empty last name
                .termsAccepted(false)    // Terms not accepted
                .privacyPolicyAccepted(false)  // Privacy policy not accepted
                .build();

        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl + "/register", invalidDTO, String.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
    }

    @Test
    @DisplayName("Repository: Should find user by email")
    void findByEmail_ExistingEmail_ReturnsUser() {
        // Register user first
        restTemplate.postForEntity(baseUrl + "/register", registrationDTO, String.class);

        var result = userRepository.findByEmail("integration@example.com");
        assertTrue(result.isPresent());
        assertEquals("integration@example.com", result.get().getEmail());
        assertEquals("integrationuser", result.get().getUsername());
    }

    @Test
    @DisplayName("Repository: Should check if email exists")
    void existsByEmail_ExistingEmail_ReturnsTrue() {
        // Register user first
        restTemplate.postForEntity(baseUrl + "/register", registrationDTO, String.class);

        boolean exists = userRepository.existsByEmail("integration@example.com");
        assertTrue(exists);
    }

    @Test
    @DisplayName("Repository: Should find active user by email")
    void findActiveUserByEmail_ActiveUser_ReturnsUser() {
        // Register user first
        restTemplate.postForEntity(baseUrl + "/register", registrationDTO, String.class);

        var result = userRepository.findActiveUserByEmail("integration@example.com");
        assertTrue(result.isPresent());
        assertEquals("integration@example.com", result.get().getEmail());
        assertTrue(result.get().getAccountEnabled());
        assertFalse(result.get().getAccountLocked());
    }
}
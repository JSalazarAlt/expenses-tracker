package com.suyos.tracker.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import com.suyos.tracker.dto.ExpenseDTO;
import com.suyos.tracker.dto.UserLoginDTO;
import com.suyos.tracker.dto.UserRegistrationDTO;
import com.suyos.tracker.model.Category;
import com.suyos.tracker.model.Expense;
import com.suyos.tracker.model.User;
import com.suyos.tracker.repository.ExpenseRepository;
import com.suyos.tracker.repository.UserRepository;

/**
 * Integration tests for ExpenseController.
 * 
 * @author Joel Salazar
 * @since 1.0
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
@DisplayName("ExpenseController Integration Tests")
class ExpenseControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;


    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Expense testExpense;
    private ExpenseDTO expenseDTO;


    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/expenses";
        expenseRepository.deleteAll();
        userRepository.deleteAll();

        // Create and register test user
        UserRegistrationDTO registrationDTO = UserRegistrationDTO.builder()
                .email("test@example.com")
                .password("Password123!")
                .username("testuser")
                .firstName("Test")
                .lastName("User")
                .termsAccepted(true)
                .privacyPolicyAccepted(true)
                .build();

        restTemplate.postForEntity("http://localhost:" + port + "/api/users/register", 
                registrationDTO, String.class);

        // Login to get auth token
        UserLoginDTO loginDTO = UserLoginDTO.builder()
                .email("test@example.com")
                .password("Password123!")
                .build();

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/users/login", loginDTO, String.class);

        // Get the actual user from database
        testUser = userRepository.findByEmail("test@example.com").orElseThrow();

        // Create test expense
        testExpense = Expense.builder()
                .description("Integration Test Expense")
                .amount(new BigDecimal("50.00"))
                .date(LocalDate.of(2024, 1, 15))
                .category(Category.FOOD)
                .user(testUser)
                .build();
        testExpense = expenseRepository.save(testExpense);

        expenseDTO = ExpenseDTO.builder()
                .description("New Integration Expense")
                .amount(new BigDecimal("75.00"))
                .date(LocalDate.of(2024, 1, 20))
                .category(Category.TRANSPORTATION)
                .build();
    }

    private HttpHeaders createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("jwt-token");
        return headers;
    }

    @Test
    @DisplayName("Should create expense for authenticated user")
    void createExpense_AuthenticatedUser_CreatesExpense() {
        HttpEntity<ExpenseDTO> request = new HttpEntity<>(expenseDTO, createAuthHeaders());
        
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl, HttpMethod.POST, request, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        String responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.contains("New Integration Expense"));
    }

    @Test
    @DisplayName("Should return 401 for unauthenticated requests")
    void getAllExpenses_Unauthenticated_ReturnsUnauthorized() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @DisplayName("Repository: Should find expenses by category")
    void findByExpenseCategory_ExistingCategory_ReturnsExpenses() {
        Expense transportExpense = Expense.builder()
                .description("Gas Station")
                .amount(new BigDecimal("60.00"))
                .date(LocalDate.of(2024, 1, 20))
                .category(Category.TRANSPORTATION)
                .user(testUser)
                .build();
        expenseRepository.save(transportExpense);

        var result = expenseRepository.findByUserIdAndCategory(testUser.getId(), Category.FOOD, 
            org.springframework.data.domain.PageRequest.of(0, 10));
        
        assertEquals(1, result.getContent().size());
        assertEquals("Integration Test Expense", result.getContent().get(0).getDescription());
        assertEquals(Category.FOOD, result.getContent().get(0).getCategory());
    }

    @Test
    @DisplayName("Repository: Should return empty page when no expenses match category")
    void findByExpenseCategory_NonExistingCategory_ReturnsEmptyPage() {
        var result = expenseRepository.findByUserIdAndCategory(testUser.getId(), Category.HEALTHCARE, 
            org.springframework.data.domain.PageRequest.of(0, 10));
        
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
    }
}
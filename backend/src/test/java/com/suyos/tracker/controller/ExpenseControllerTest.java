package com.suyos.tracker.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suyos.tracker.dto.ExpenseDTO;
import com.suyos.tracker.dto.PagedResponse;
import com.suyos.tracker.model.Category;
import com.suyos.tracker.service.ExpenseService;
import com.suyos.tracker.service.UserService;

/**
 * Controller tests for ExpenseController.
 * 
 * @author Joel Salazar
 * @since 1.0
 */
@WebMvcTest(ExpenseController.class)
@DisplayName("ExpenseController Tests")
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExpenseDTO testExpenseDTO;
    private PagedResponse<ExpenseDTO> pagedResponse;

    @BeforeEach
    void setUp() {
        testExpenseDTO = ExpenseDTO.builder()
                .id(1L)
                .description("Test Expense")
                .amount(new BigDecimal("25.50"))
                .date(LocalDate.of(2024, 1, 15))
                .category(Category.FOOD)
                .build();

        pagedResponse = PagedResponse.<ExpenseDTO>builder()
                .content(List.of(testExpenseDTO))
                .currentPage(0)
                .totalPages(1)
                .totalElements(1L)
                .size(10)
                .first(true)
                .last(true)
                .build();
    }

    @Test
    @DisplayName("Should get all expenses successfully")
    void getAllExpenses_ValidRequest_ReturnsPagedResponse() throws Exception {
        // Given
        when(userService.getCurrentUserId()).thenReturn(1L);
        when(expenseService.getAllExpensesPaginated(1L, 0, 10, "date", "desc", null, null, null))
                .thenReturn(pagedResponse);

        // When & Then
        mockMvc.perform(get("/api/expenses")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].description").value("Test Expense"))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(jsonPath("$.totalElements").value(1));

        verify(userService).getCurrentUserId();
        verify(expenseService).getAllExpensesPaginated(1L, 0, 10, "date", "desc", null, null, null);
    }

    @Test
    @DisplayName("Should create expense successfully")
    void createExpense_ValidExpenseDTO_ReturnsCreatedExpense() throws Exception {
        // Given
        ExpenseDTO newExpenseDTO = ExpenseDTO.builder()
                .description("New Expense")
                .amount(new BigDecimal("30.00"))
                .date(LocalDate.of(2024, 1, 20))
                .category(Category.TRANSPORTATION)
                .build();

        ExpenseDTO createdExpenseDTO = ExpenseDTO.builder()
                .id(2L)
                .description("New Expense")
                .amount(new BigDecimal("30.00"))
                .date(LocalDate.of(2024, 1, 20))
                .category(Category.TRANSPORTATION)
                .build();

        when(userService.getCurrentUserId()).thenReturn(1L);
        when(expenseService.createExpense(any(ExpenseDTO.class), eq(1L))).thenReturn(createdExpenseDTO);

        // When & Then
        mockMvc.perform(post("/api/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newExpenseDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.description").value("New Expense"));

        verify(userService).getCurrentUserId();
        verify(expenseService).createExpense(any(ExpenseDTO.class), eq(1L));
    }

    @Test
    @DisplayName("Should get expense by ID successfully")
    void getExpense_ExistingId_ReturnsExpense() throws Exception {
        // Given
        when(userService.getCurrentUserId()).thenReturn(1L);
        when(expenseService.getExpenseById(1L, 1L)).thenReturn(testExpenseDTO);

        // When & Then
        mockMvc.perform(get("/api/expenses/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Test Expense"));

        verify(userService).getCurrentUserId();
        verify(expenseService).getExpenseById(1L, 1L);
    }

    @Test
    @DisplayName("Should return 404 when expense not found")
    void getExpense_NonExistingId_ReturnsNotFound() throws Exception {
        // Given
        when(userService.getCurrentUserId()).thenReturn(1L);
        when(expenseService.getExpenseById(999L, 1L)).thenThrow(new RuntimeException("Expense not found"));

        // When & Then
        mockMvc.perform(get("/api/expenses/999"))
                .andExpect(status().isNotFound());

        verify(userService).getCurrentUserId();
        verify(expenseService).getExpenseById(999L, 1L);
    }

    @Test
    @DisplayName("Should delete expense successfully")
    void deleteExpense_ExistingId_ReturnsNoContent() throws Exception {
        // Given
        when(userService.getCurrentUserId()).thenReturn(1L);
        doNothing().when(expenseService).deleteExpenseById(1L, 1L);

        // When & Then
        mockMvc.perform(delete("/api/expenses/1"))
                .andExpect(status().isNoContent());

        verify(userService).getCurrentUserId();
        verify(expenseService).deleteExpenseById(1L, 1L);
    }

    @Test
    @DisplayName("Should return 404 when deleting non-existing expense")
    void deleteExpense_NonExistingId_ReturnsNotFound() throws Exception {
        // Given
        when(userService.getCurrentUserId()).thenReturn(1L);
        doThrow(new RuntimeException("Expense not found")).when(expenseService).deleteExpenseById(999L, 1L);

        // When & Then
        mockMvc.perform(delete("/api/expenses/999"))
                .andExpect(status().isNotFound());

        verify(userService).getCurrentUserId();
        verify(expenseService).deleteExpenseById(999L, 1L);
    }
    
}
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
        when(expenseService.getExpensesPaginated(0, 10, "expenseDate", "desc", null, null, null))
                .thenReturn(pagedResponse);

        // When & Then
        mockMvc.perform(get("/api/expenses")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].expenseId").value(1))
                .andExpect(jsonPath("$.content[0].expenseDescription").value("Test Expense"))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(jsonPath("$.totalElements").value(1));

        verify(expenseService).getExpensesPaginated(0, 10, "expenseDate", "desc", null, null, null);
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

        when(expenseService.createExpense(any(ExpenseDTO.class))).thenReturn(createdExpenseDTO);

        // When & Then
        mockMvc.perform(post("/api/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newExpenseDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.expenseId").value(2))
                .andExpect(jsonPath("$.expenseDescription").value("New Expense"));

        verify(expenseService).createExpense(any(ExpenseDTO.class));
    }

    @Test
    @DisplayName("Should get expense by ID successfully")
    void getExpense_ExistingId_ReturnsExpense() throws Exception {
        // Given
        when(expenseService.getExpenseById(1L)).thenReturn(testExpenseDTO);

        // When & Then
        mockMvc.perform(get("/api/expenses/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.expenseId").value(1))
                .andExpect(jsonPath("$.expenseDescription").value("Test Expense"));

        verify(expenseService).getExpenseById(1L);
    }

    @Test
    @DisplayName("Should return 404 when expense not found")
    void getExpense_NonExistingId_ReturnsNotFound() throws Exception {
        // Given
        when(expenseService.getExpenseById(999L)).thenThrow(new RuntimeException("Expense not found"));

        // When & Then
        mockMvc.perform(get("/api/expenses/999"))
                .andExpect(status().isNotFound());

        verify(expenseService).getExpenseById(999L);
    }

    @Test
    @DisplayName("Should delete expense successfully")
    void deleteExpense_ExistingId_ReturnsNoContent() throws Exception {
        // Given
        doNothing().when(expenseService).deleteExpense(1L);

        // When & Then
        mockMvc.perform(delete("/api/expenses/1"))
                .andExpect(status().isNoContent());

        verify(expenseService).deleteExpense(1L);
    }

    @Test
    @DisplayName("Should return 404 when deleting non-existing expense")
    void deleteExpense_NonExistingId_ReturnsNotFound() throws Exception {
        // Given
        doThrow(new RuntimeException("Expense not found")).when(expenseService).deleteExpense(999L);

        // When & Then
        mockMvc.perform(delete("/api/expenses/999"))
                .andExpect(status().isNotFound());

        verify(expenseService).deleteExpense(999L);
    }
    
}
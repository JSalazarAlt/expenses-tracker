package com.suyos.tracker.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.suyos.tracker.dto.ExpenseDTO;
import com.suyos.tracker.model.Category;
import com.suyos.tracker.model.Expense;

/**
 * Tests for ExpenseMapper.
 * 
 * @author Joel Salazar
 * @since 1.0
 */
@SpringBootTest
@DisplayName("ExpenseMapper Tests")
class ExpenseMapperTest {

    @Autowired
    private ExpenseMapper expenseMapper;

    @Test
    @DisplayName("Should convert Expense entity to ExpenseDTO")
    void toDTO_ValidExpense_ReturnsExpenseDTO() {
        // Given
        Expense expense = Expense.builder()
                .id(1L)
                .description("Test Expense")
                .amount(new BigDecimal("25.50"))
                .date(LocalDate.of(2024, 1, 15))
                .category(Category.FOOD)
                .build();

        // When
        ExpenseDTO result = expenseMapper.toDTO(expense);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Expense", result.getDescription());
        assertEquals(new BigDecimal("25.50"), result.getAmount());
        assertEquals(LocalDate.of(2024, 1, 15), result.getDate());
        assertEquals(Category.FOOD, result.getCategory());
    }

    @Test
    @DisplayName("Should convert ExpenseDTO to Expense entity")
    void toEntity_ValidExpenseDTO_ReturnsExpense() {
        // Given
        ExpenseDTO expenseDTO = ExpenseDTO.builder()
                .id(1L)
                .description("Test Expense")
                .amount(new BigDecimal("25.50"))
                .date(LocalDate.of(2024, 1, 15))
                .category(Category.FOOD)
                .build();

        // When
        Expense result = expenseMapper.toEntity(expenseDTO);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Expense", result.getDescription());
        assertEquals(new BigDecimal("25.50"), result.getAmount());
        assertEquals(LocalDate.of(2024, 1, 15), result.getDate());
        assertEquals(Category.FOOD, result.getCategory());
    }

    @Test
    @DisplayName("Should handle null values in mapping")
    void toDTO_NullExpense_ReturnsNull() {
        // When
        ExpenseDTO result = expenseMapper.toDTO(null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle null values in reverse mapping")
    void toEntity_NullExpenseDTO_ReturnsNull() {
        // When
        Expense result = expenseMapper.toEntity(null);

        // Then
        assertNull(result);
    }
    
}
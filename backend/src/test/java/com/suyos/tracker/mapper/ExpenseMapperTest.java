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
                .expenseId(1L)
                .expenseDescription("Test Expense")
                .expenseAmount(new BigDecimal("25.50"))
                .expenseDate(LocalDate.of(2024, 1, 15))
                .expenseCategory(Category.FOOD)
                .build();

        // When
        ExpenseDTO result = expenseMapper.toDTO(expense);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getExpenseId());
        assertEquals("Test Expense", result.getExpenseDescription());
        assertEquals(new BigDecimal("25.50"), result.getExpenseAmount());
        assertEquals(LocalDate.of(2024, 1, 15), result.getExpenseDate());
        assertEquals(Category.FOOD, result.getExpenseCategory());
    }

    @Test
    @DisplayName("Should convert ExpenseDTO to Expense entity")
    void toEntity_ValidExpenseDTO_ReturnsExpense() {
        // Given
        ExpenseDTO expenseDTO = ExpenseDTO.builder()
                .expenseId(1L)
                .expenseDescription("Test Expense")
                .expenseAmount(new BigDecimal("25.50"))
                .expenseDate(LocalDate.of(2024, 1, 15))
                .expenseCategory(Category.FOOD)
                .build();

        // When
        Expense result = expenseMapper.toEntity(expenseDTO);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getExpenseId());
        assertEquals("Test Expense", result.getExpenseDescription());
        assertEquals(new BigDecimal("25.50"), result.getExpenseAmount());
        assertEquals(LocalDate.of(2024, 1, 15), result.getExpenseDate());
        assertEquals(Category.FOOD, result.getExpenseCategory());
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
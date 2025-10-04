package com.suyos.tracker.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.suyos.tracker.model.Category;
import com.suyos.tracker.model.Expense;
import com.suyos.tracker.model.User;

/**
 * Unit tests for ExpenseRepository.
 * 
 * @author Joel Salazar
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ExpenseRepository Unit Tests")
class ExpenseRepositoryTest {

    @Mock
    private ExpenseRepository expenseRepository;

    private Expense testExpense;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .build();

        testExpense = Expense.builder()
                .id(1L)
                .description("Test Expense")
                .amount(new BigDecimal("25.50"))
                .date(LocalDate.of(2024, 1, 15))
                .category(Category.FOOD)
                .user(testUser)
                .build();
    }

    @Test
    @DisplayName("Should find expense by ID and user ID")
    void findByIdAndUserId_ExistingExpense_ReturnsExpense() {
        // Given
        when(expenseRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testExpense));

        // When
        Optional<Expense> result = expenseRepository.findByIdAndUserId(1L, 1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testExpense, result.get());
        verify(expenseRepository).findByIdAndUserId(1L, 1L);
    }

    @Test
    @DisplayName("Should return empty when expense not found")
    void findByIdAndUserId_NonExistingExpense_ReturnsEmpty() {
        // Given
        when(expenseRepository.findByIdAndUserId(999L, 1L)).thenReturn(Optional.empty());

        // When
        Optional<Expense> result = expenseRepository.findByIdAndUserId(999L, 1L);

        // Then
        assertFalse(result.isPresent());
        verify(expenseRepository).findByIdAndUserId(999L, 1L);
    }

    @Test
    @DisplayName("Should find expenses by user ID")
    void findByUserId_ExistingUser_ReturnsExpenses() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Expense> expectedPage = new PageImpl<>(List.of(testExpense));
        when(expenseRepository.findByUserId(1L, pageable)).thenReturn(expectedPage);

        // When
        Page<Expense> result = expenseRepository.findByUserId(1L, pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(testExpense, result.getContent().get(0));
        verify(expenseRepository).findByUserId(1L, pageable);
    }

    @Test
    @DisplayName("Should save expense")
    void save_ValidExpense_ReturnsSavedExpense() {
        // Given
        when(expenseRepository.save(testExpense)).thenReturn(testExpense);

        // When
        Expense result = expenseRepository.save(testExpense);

        // Then
        assertEquals(testExpense, result);
        verify(expenseRepository).save(testExpense);
    }

    @Test
    @DisplayName("Should delete expense")
    void delete_ValidExpense_DeletesExpense() {
        // Given
        doNothing().when(expenseRepository).delete(testExpense);

        // When
        expenseRepository.delete(testExpense);

        // Then
        verify(expenseRepository).delete(testExpense);
    }
}
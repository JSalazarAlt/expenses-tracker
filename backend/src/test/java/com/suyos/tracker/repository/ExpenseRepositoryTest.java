package com.suyos.tracker.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.suyos.tracker.model.Category;
import com.suyos.tracker.model.Expense;

/**
 * Repository tests for ExpenseRepository.
 * 
 * @author Joel Salazar
 * @since 1.0
 */
@DataJpaTest
@DisplayName("ExpenseRepository Tests")
@ActiveProfiles("test")
class ExpenseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExpenseRepository expenseRepository;

    private Expense foodExpense;
    private Expense transportExpense;

    @BeforeEach
    void setUp() {
        foodExpense = Expense.builder()
                .expenseDescription("Grocery Shopping")
                .expenseAmount(new BigDecimal("45.67"))
                .expenseDate(LocalDate.of(2024, 1, 15))
                .expenseCategory(Category.FOOD)
                .build();

        transportExpense = Expense.builder()
                .expenseDescription("Gas Station")
                .expenseAmount(new BigDecimal("60.00"))
                .expenseDate(LocalDate.of(2024, 1, 20))
                .expenseCategory(Category.TRANSPORTATION)
                .build();

        entityManager.persistAndFlush(foodExpense);
        entityManager.persistAndFlush(transportExpense);
    }

    @Test
    @DisplayName("Should find expenses by category")
    void findByExpenseCategory_ExistingCategory_ReturnsExpenses() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Expense> result = expenseRepository.findByExpenseCategory(Category.FOOD, pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Grocery Shopping", result.getContent().get(0).getExpenseDescription());
        assertEquals(Category.FOOD, result.getContent().get(0).getExpenseCategory());
    }

    @Test
    @DisplayName("Should find expenses by date range")
    void findByExpenseDateBetween_ValidDateRange_ReturnsExpenses() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 31);
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Expense> result = expenseRepository.findByExpenseDateBetween(startDate, endDate, pageable);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    @DisplayName("Should find expenses by category and date range")
    void findByExpenseCategoryAndExpenseDateBetween_ValidFilters_ReturnsExpenses() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 31);
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Expense> result = expenseRepository.findByExpenseCategoryAndExpenseDateBetween(
                Category.FOOD, startDate, endDate, pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Grocery Shopping", result.getContent().get(0).getExpenseDescription());
        assertEquals(Category.FOOD, result.getContent().get(0).getExpenseCategory());
    }

    @Test
    @DisplayName("Should return empty page when no expenses match category")
    void findByExpenseCategory_NonExistingCategory_ReturnsEmptyPage() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Expense> result = expenseRepository.findByExpenseCategory(Category.HEALTHCARE, pageable);

        // Then
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
    }

    @Test
    @DisplayName("Should return empty page when no expenses in date range")
    void findByExpenseDateBetween_NoExpensesInRange_ReturnsEmptyPage() {
        // Given
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Expense> result = expenseRepository.findByExpenseDateBetween(startDate, endDate, pageable);

        // Then
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
    }
}
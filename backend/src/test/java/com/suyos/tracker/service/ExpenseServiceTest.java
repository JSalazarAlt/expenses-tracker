package com.suyos.tracker.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.suyos.tracker.dto.ExpenseDTO;
import com.suyos.tracker.dto.PagedResponse;
import com.suyos.tracker.mapper.ExpenseMapper;
import com.suyos.tracker.model.Category;
import com.suyos.tracker.model.Expense;
import com.suyos.tracker.repository.ExpenseRepository;

/**
 * Unit tests for ExpenseService.
 * 
 * @author Joel Salazar
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ExpenseService Unit Tests")
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ExpenseMapper expenseMapper;

    @InjectMocks
    private ExpenseService expenseService;

    private Expense testExpense;
    private ExpenseDTO testExpenseDTO;

    @BeforeEach
    void setUp() {
        testExpense = Expense.builder()
                .id(1L)
                .description("Test Expense")
                .amount(new BigDecimal("25.50"))
                .date(LocalDate.of(2024, 1, 15))
                .category(Category.FOOD)
                .build();

        testExpenseDTO = ExpenseDTO.builder()
                .id(1L)
                .description("Test Expense")
                .amount(new BigDecimal("25.50"))
                .date(LocalDate.of(2024, 1, 15))
                .category(Category.FOOD)
                .build();
    }

    @Test
    @DisplayName("Should get paginated expenses with no filters")
    void getExpensesPaginated_NoFilters_ReturnsPagedResponse() {
        // Given
        Pageable pageable = PageRequest.of(0, 10, Sort.by("expenseDate").descending());
        Page<Expense> expensePage = new PageImpl<>(List.of(testExpense), pageable, 1);
        
        when(expenseRepository.findAll(pageable)).thenReturn(expensePage);
        when(expenseMapper.toDTO(testExpense)).thenReturn(testExpenseDTO);

        // When
        PagedResponse<ExpenseDTO> result = expenseService.getExpensesPaginated(
                0, 10, "expenseDate", "desc", null, null, null);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(testExpenseDTO, result.getContent().get(0));
        assertEquals(0, result.getCurrentPage());
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getTotalElements());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());

        verify(expenseRepository).findAll(pageable);
        verify(expenseMapper).toDTO(testExpense);
    }

    @Test
    @DisplayName("Should get expense by ID successfully")
    void getExpenseById_ExistingId_ReturnsExpenseDTO() {
        // Given
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(testExpense));
        when(expenseMapper.toDTO(testExpense)).thenReturn(testExpenseDTO);

        // When
        ExpenseDTO result = expenseService.getExpenseById(1L);

        // Then
        assertNotNull(result);
        assertEquals(testExpenseDTO, result);
        verify(expenseRepository).findById(1L);
        verify(expenseMapper).toDTO(testExpense);
    }

    @Test
    @DisplayName("Should throw exception when expense not found by ID")
    void getExpenseById_NonExistingId_ThrowsException() {
        // Given
        when(expenseRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
                () -> expenseService.getExpenseById(999L));
        
        assertEquals("Expense not found with id: 999", exception.getMessage());
        verify(expenseRepository).findById(999L);
        verifyNoInteractions(expenseMapper);
    }

    @Test
    @DisplayName("Should create expense successfully")
    void createExpense_ValidExpenseDTO_ReturnsCreatedExpenseDTO() {
        // Given
        Expense newExpense = Expense.builder()
                .description("New Expense")
                .amount(new BigDecimal("30.00"))
                .date(LocalDate.of(2024, 1, 20))
                .category(Category.TRANSPORTATION)
                .build();

        Expense savedExpense = Expense.builder()
                .id(2L)
                .description("New Expense")
                .amount(new BigDecimal("30.00"))
                .date(LocalDate.of(2024, 1, 20))
                .category(Category.TRANSPORTATION)
                .build();

        ExpenseDTO newExpenseDTO = ExpenseDTO.builder()
                .description("New Expense")
                .amount(new BigDecimal("30.00"))
                .date(LocalDate.of(2024, 1, 20))
                .category(Category.TRANSPORTATION)
                .build();

        ExpenseDTO savedExpenseDTO = ExpenseDTO.builder()
                .id(2L)
                .description("New Expense")
                .amount(new BigDecimal("30.00"))
                .date(LocalDate.of(2024, 1, 20))
                .category(Category.TRANSPORTATION)
                .build();

        when(expenseMapper.toEntity(newExpenseDTO)).thenReturn(newExpense);
        when(expenseRepository.save(newExpense)).thenReturn(savedExpense);
        when(expenseMapper.toDTO(savedExpense)).thenReturn(savedExpenseDTO);

        // When
        ExpenseDTO result = expenseService.createExpense(newExpenseDTO);

        // Then
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("New Expense", result.getDescription());
        verify(expenseMapper).toEntity(newExpenseDTO);
        verify(expenseRepository).save(newExpense);
        verify(expenseMapper).toDTO(savedExpense);
    }

    @Test
    @DisplayName("Should delete expense successfully")
    void deleteExpense_ExistingId_DeletesExpense() {
        // Given
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(testExpense));

        // When
        expenseService.deleteExpense(1L);

        // Then
        verify(expenseRepository).findById(1L);
        verify(expenseRepository).delete(testExpense);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existing expense")
    void deleteExpense_NonExistingId_ThrowsException() {
        // Given
        when(expenseRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
                () -> expenseService.deleteExpense(999L));
        
        assertEquals("Expense not found with id: 999", exception.getMessage());
        verify(expenseRepository).findById(999L);
        verify(expenseRepository, never()).delete(any());
    }
    
}
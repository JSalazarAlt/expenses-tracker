package com.suyos.tracker.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.suyos.tracker.dto.ExpenseDTO;
import com.suyos.tracker.dto.PagedResponse;
import com.suyos.tracker.mapper.ExpenseMapper;
import com.suyos.tracker.model.Expense;
import com.suyos.tracker.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service class containing business logic for expense management operations.
 * 
 * This service acts as an intermediary between the controller layer and the
 * data access layer, implementing the core business rules and data transformations
 * for expense operations. It handles entity-DTO conversions and provides both
 * paginated and non-paginated data access methods.
 * 
 * @author Joel Salazar
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ExpenseService {
        
    /** Repository for expense data access operations */
    private final ExpenseRepository expenseRepository;
    
    /** Mapper for converting between entities and DTOs */
    private final ExpenseMapper expenseMapper;
    
    /**
     * Retrieves all expenses without pagination.
     * 
     * This method fetches all expense records from the database and converts
     * them to DTOs. Use with caution for large datasets as it loads all data
     * into memory. Consider using the paginated version for better performance.
     * 
     * @return List of all expenses as DTOs
     */
    public List<ExpenseDTO> getAllExpenses() {
        // Fetch all expenses from database
        List<Expense> expenses = expenseRepository.findAll();
        
        // Convert entities to DTOs using mapper
        return expenses.stream()
            .map(expenseMapper::toDTO)
            .toList();
    }
    
    /**
     * Retrieves expenses with pagination and sorting.
     * 
     * This method provides efficient data access for large datasets by implementing
     * server-side pagination. Results are sorted by expense date in descending order
     * (most recent first) to show the latest expenses at the top.
     * 
     * @param page Zero-based page index
     * @param size Number of records per page
     * @return PagedResponse containing expense DTOs and pagination metadata
     */
    public PagedResponse<ExpenseDTO> getExpensesPaginated(int page, int size) {
        // Create pageable request with sorting by date descending
        Pageable pageable = PageRequest.of(page, size, Sort.by("expenseDate").descending());
        
        // Fetch paginated data from repository
        Page<Expense> expensePage = expenseRepository.findAll(pageable);
        
        // Convert page content to DTOs
        List<ExpenseDTO> expenses = expensePage.getContent()
            .stream()
            .map(expenseMapper::toDTO)
            .toList();
        
        // Build response with pagination metadata
        return PagedResponse.<ExpenseDTO>builder()
            .content(expenses)
            .currentPage(expensePage.getNumber())
            .totalPages(expensePage.getTotalPages())
            .totalElements(expensePage.getTotalElements())
            .size(expensePage.getSize())
            .first(expensePage.isFirst())
            .last(expensePage.isLast())
            .build();
    }

    /**
     * Retrieves a specific expense by its ID.
     * 
     * @param id The unique identifier of the expense
     * @return ExpenseDTO containing the expense data
     * @throws RuntimeException if no expense exists with the given ID
     */
    public ExpenseDTO getExpenseById(Long id) {
        // Find expense by ID or throw exception if not found
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        
        // Convert entity to DTO
        return expenseMapper.toDTO(expense);
    }

    /**
     * Creates a new expense record.
     * 
     * The ID field in the DTO is ignored as the database will auto-generate
     * a unique identifier. Timestamps (createdAt, updatedAt) are automatically
     * set by Hibernate annotations.
     * 
     * @param expenseDTO The expense data to create
     * @return ExpenseDTO containing the created expense with generated ID
     */
    public ExpenseDTO createExpense(ExpenseDTO expenseDTO) {
        // Convert DTO to entity (ID will be null for new records)
        Expense expense = expenseMapper.toEntity(expenseDTO);
        
        // Save entity to database (ID and timestamps auto-generated)
        Expense saved = expenseRepository.save(expense);
        
        // Return saved entity as DTO with generated ID
        return expenseMapper.toDTO(saved);
    }
    
    /**
     * Updates an existing expense record.
     * 
     * This method performs a partial update, modifying only the provided fields
     * while preserving the original ID and timestamps. The updatedAt timestamp
     * is automatically updated by Hibernate.
     * 
     * @param id The ID of the expense to update
     * @param expenseDTO The updated expense data
     * @return ExpenseDTO containing the updated expense
     * @throws RuntimeException if no expense exists with the given ID
     */
    public ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDTO) {
        // Validate that expense exists before updating
        Expense existingExpense = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        
        // Update modifiable fields (preserve ID, createdAt)
        existingExpense.setExpenseDescription(expenseDTO.getExpenseDescription());
        existingExpense.setExpenseAmount(expenseDTO.getExpenseAmount());
        existingExpense.setExpenseDate(expenseDTO.getExpenseDate());
        existingExpense.setExpenseCategory(expenseDTO.getExpenseCategory());
        
        // Save updated entity (updatedAt timestamp automatically set)
        Expense updated = expenseRepository.save(existingExpense);
        
        // Return updated entity as DTO
        return expenseMapper.toDTO(updated);
    }

    /**
     * Deletes an expense record by ID.
     * 
     * This method performs a soft validation by checking if the expense exists
     * before attempting deletion to provide meaningful error messages.
     * 
     * @param id The ID of the expense to delete
     * @throws RuntimeException if no expense exists with the given ID
     */
    public void deleteExpense(Long id) {
        // Validate expense exists before deletion
        if (!expenseRepository.existsById(id)) {
            throw new RuntimeException("Expense not found with id: " + id);
        }
        
        // Perform deletion
        expenseRepository.deleteById(id);
    }

}

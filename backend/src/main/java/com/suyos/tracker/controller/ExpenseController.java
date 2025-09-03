package com.suyos.tracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suyos.tracker.dto.ExpenseDTO;
import com.suyos.tracker.dto.PagedResponse;
import com.suyos.tracker.service.ExpenseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for expense management operations.
 * 
 * This controller provides HTTP endpoints for CRUD operations on expenses,
 * including paginated retrieval, creation, updates, and deletion.
 * All endpoints return appropriate HTTP status codes and handle errors gracefully.
 * 
 * CORS is enabled for the React development server to allow cross-origin requests
 * during development. In production, this should be configured more restrictively.
 * 
 * @author Joel Salazar
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:5173") // React dev server
public class ExpenseController {
        
    /** Service layer for expense business logic */
    private final ExpenseService expenseService;
    
    /**
     * Retrieves expenses with pagination support.
     * 
     * This endpoint supports server-side pagination to efficiently handle large datasets.
     * Results are sorted by expense date in descending order (most recent first).
     * 
     * @param page Zero-based page number (default: 0)
     * @param size Number of records per page (default: 10)
     * @return ResponseEntity containing paginated expense data and metadata
     */
    @GetMapping
    public ResponseEntity<PagedResponse<ExpenseDTO>> getAllExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        // Fetch paginated expenses from service layer
        PagedResponse<ExpenseDTO> expenses = expenseService.getExpensesPaginated(page, size);
        
        // Return successful response with data
        return ResponseEntity.ok(expenses);
    }
    
    /**
     * Creates a new expense record.
     * 
     * The request body is validated using Bean Validation annotations.
     * Returns HTTP 201 (Created) status on successful creation.
     * 
     * @param dto The expense data to create (validated)
     * @return ResponseEntity containing the created expense with generated ID
     */
    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@Valid @RequestBody ExpenseDTO dto) {
        // Create new expense through service layer
        ExpenseDTO created = expenseService.createExpense(dto);
        
        // Return 201 Created status with the new expense data
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    /**
     * Retrieves a specific expense by ID.
     * 
     * Returns HTTP 404 (Not Found) if no expense exists with the given ID.
     * 
     * @param id The unique identifier of the expense
     * @return ResponseEntity containing the expense data or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getExpense(@PathVariable Long id) {
        try {
            // Attempt to retrieve expense by ID
            ExpenseDTO expense = expenseService.getExpenseById(id);
            return ResponseEntity.ok(expense);
        } catch (RuntimeException e) {
            // Return 404 if expense not found
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Updates an existing expense record.
     * 
     * The request body is validated and the expense ID must exist.
     * Returns HTTP 404 (Not Found) if no expense exists with the given ID.
     * 
     * @param id The ID of the expense to update
     * @param dto The updated expense data (validated)
     * @return ResponseEntity containing the updated expense or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseDTO dto) {
        try {
            // Attempt to update expense
            ExpenseDTO updated = expenseService.updateExpense(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            // Return 404 if expense not found
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Deletes an expense record by ID.
     * 
     * Returns HTTP 204 (No Content) on successful deletion.
     * Returns HTTP 404 (Not Found) if no expense exists with the given ID.
     * 
     * @param id The ID of the expense to delete
     * @return ResponseEntity with no content on success or 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        try {
            // Attempt to delete expense
            expenseService.deleteExpense(id);
            
            // Return 204 No Content on successful deletion
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Return 404 if expense not found
            return ResponseEntity.notFound().build();
        }
    }
    
}

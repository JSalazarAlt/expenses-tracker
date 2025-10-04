package com.suyos.tracker.controller;

import java.time.LocalDate;

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
import com.suyos.tracker.model.Category;
import com.suyos.tracker.service.ExpenseService;
import com.suyos.tracker.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Expenses", description = "Expense management operations")
@SecurityRequirement(name = "Bearer Authentication")
public class ExpenseController {
        
    /** Service layer for expense business logic */
    private final ExpenseService expenseService;
    
    /** Service layer for user operations */
    private final UserService userService;
    
    /**
     * Retrieves expenses with pagination, sorting, and filtering support for the authenticated user.
     * 
     * This endpoint supports server-side pagination to efficiently handle large datasets.
     * Results are filtered by the current user and can be further filtered by category and date range.
     * 
     * @param page Zero-based page number (default: 0)
     * @param size Number of records per page (default: 10)
     * @param sortBy Field name to sort by (default: "date")
     * @param sortDir Sort direction - "asc" or "desc" (default: "desc")
     * @param category Optional category filter (null for no filter)
     * @param startDate Optional start date filter (null for no filter)
     * @param endDate Optional end date filter (null for no filter)
     * @return ResponseEntity containing paginated expense data and metadata
     */
    @GetMapping
    @Operation(summary = "Get paginated expenses", description = "Retrieves expenses with pagination, sorting, and filtering for the authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved expenses"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Access denied")
    })
    public ResponseEntity<PagedResponse<ExpenseDTO>> getAllExpenses(
            @Parameter(description = "Zero-based page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of records per page (max 100)") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by") @RequestParam(defaultValue = "date") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "desc") String sortDir,
            @Parameter(description = "Filter by category") @RequestParam(required = false) Category category,
            @Parameter(description = "Filter by start date (YYYY-MM-DD)") @RequestParam(required = false) LocalDate startDate,
            @Parameter(description = "Filter by end date (YYYY-MM-DD)") @RequestParam(required = false) LocalDate endDate) {
        
        // Get current user ID from authentication context
        Long userId = userService.getCurrentUserId();
        
        // Fetch paginated expenses from service layer for current user
        PagedResponse<ExpenseDTO> expenses = expenseService.getAllExpensesPaginated(userId, page, 
            size, sortBy, sortDir, category, startDate, endDate);
        
        // Return successful response with data
        return ResponseEntity.ok(expenses);
    }
    
    /**
     * Creates a new expense record for the authenticated user.
     * 
     * The request body is validated using Bean Validation annotations.
     * Returns HTTP 201 (Created) status on successful creation.
     * 
     * @param expenseDTO The expense data to create (validated)
     * @return ResponseEntity containing the created expense with generated ID
     */
    @PostMapping
    @Operation(summary = "Create new expense", description = "Creates a new expense record for the authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Expense created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token")
    })
    public ResponseEntity<ExpenseDTO> createExpense(@Valid @RequestBody ExpenseDTO expenseDTO) {
        // Get current user ID from authentication context
        Long userId = userService.getCurrentUserId();
        
        // Create new expense through service layer for current user
        ExpenseDTO expenseDTOCreated = expenseService.createExpense(expenseDTO, userId);
        
        // Return 201 Created status with the new expense data
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseDTOCreated);
    }
    
    /**
     * Retrieves a specific expense by ID for the authenticated user.
     * 
     * Returns HTTP 404 (Not Found) if no expense exists with the given ID for the current user.
     * 
     * @param id The unique identifier of the expense
     * @return ResponseEntity containing the expense data or 404 if not found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get expense by ID", description = "Retrieves a specific expense by ID for the authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Expense found"),
        @ApiResponse(responseCode = "404", description = "Expense not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token")
    })
    public ResponseEntity<ExpenseDTO> getExpense(@Parameter(description = "Expense ID") @PathVariable Long id) {
        try {
            // Get current user ID from authentication context
            Long userId = userService.getCurrentUserId();
            
            // Attempt to retrieve expense by ID for current user
            ExpenseDTO expenseDTO = expenseService.getExpenseById(id, userId);
            return ResponseEntity.ok(expenseDTO);
        } catch (RuntimeException e) {
            // Return 404 if expense not found
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Updates an existing expense record for the authenticated user.
     * 
     * The request body is validated and the expense ID must exist and belong to the current user.
     * Returns HTTP 404 (Not Found) if no expense exists with the given ID for the current user.
     * 
     * @param id The ID of the expense to update
     * @param expenseDTO The updated expense data (validated)
     * @return ResponseEntity containing the updated expense or 404 if not found
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update expense", description = "Updates an existing expense record for the authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Expense updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Expense not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token")
    })
    public ResponseEntity<ExpenseDTO> updateExpense(@Parameter(description = "Expense ID") @PathVariable Long id, @Valid @RequestBody ExpenseDTO expenseDTO) {
        try {
            // Get current user ID from authentication context
            Long userId = userService.getCurrentUserId();
            
            // Attempt to update expense for current user
            ExpenseDTO updated = expenseService.updateExpenseById(id, expenseDTO, userId);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            // Return 404 if expense not found
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Deletes an expense record by ID for the authenticated user.
     * 
     * Returns HTTP 204 (No Content) on successful deletion.
     * Returns HTTP 404 (Not Found) if no expense exists with the given ID for the current user.
     * 
     * @param id The ID of the expense to delete
     * @return ResponseEntity with no content on success or 404 if not found
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete expense", description = "Deletes an expense record by ID for the authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Expense deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Expense not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token")
    })
    public ResponseEntity<Void> deleteExpense(@Parameter(description = "Expense ID") @PathVariable Long id) {
        try {
            // Get current user ID from authentication context
            Long userId = userService.getCurrentUserId();
            
            // Attempt to delete expense for current user
            expenseService.deleteExpenseById(id, userId);
            
            // Return 204 No Content on successful deletion
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Return 404 if expense not found
            return ResponseEntity.notFound().build();
        }
    }
    
}
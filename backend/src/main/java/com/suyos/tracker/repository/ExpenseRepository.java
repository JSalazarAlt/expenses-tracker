package com.suyos.tracker.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suyos.tracker.model.Category;
import com.suyos.tracker.model.Expense;

/**
 * Repository interface for Expense entity data access operations.
 * 
 * This interface extends JpaRepository to provide standard CRUD operations
 * and pagination support for Expense entities. Spring Data JPA automatically
 * generates the implementation at runtime.
 * 
 * Automatically available operations include:
 * - findAll() - retrieve all expenses
 * - findById() - find expense by ID
 * - save() - create or update expense
 * - deleteById() - delete expense by ID
 * - findAll(Pageable) - paginated retrieval
 * 
 * @author Joel Salazar
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    /**
     * Finds all expenses for a specific user with pagination.
     * 
     * @param userId The ID of the user whose expenses to retrieve
     * @param pageable Pagination and sorting information
     * @return Page of expenses belonging to the specified user
     */
    Page<Expense> findByUserId(Long userId, Pageable pageable);
    
    /**
     * Finds expenses by user and category with pagination.
     * 
     * @param userId The ID of the user whose expenses to retrieve
     * @param category The expense category to filter by
     * @param pageable Pagination and sorting information
     * @return Page of expenses matching user and category criteria
     */
    Page<Expense> findByUserIdAndCategory(Long userId, Category category, Pageable pageable);
    
    /**
     * Finds expenses by user within a date range with pagination.
     * 
     * @param userId The ID of the user whose expenses to retrieve
     * @param startDate The start date (inclusive)
     * @param endDate The end date (inclusive)
     * @param pageable Pagination and sorting information
     * @return Page of expenses matching user and date range criteria
     */
    Page<Expense> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
    
    /**
     * Finds expenses by user, category and within a date range with pagination.
     * 
     * @param userId The ID of the user whose expenses to retrieve
     * @param category The expense category to filter by
     * @param startDate The start date (inclusive)
     * @param endDate The end date (inclusive)
     * @param pageable Pagination and sorting information
     * @return Page of expenses matching user, category and date range criteria
     */
    Page<Expense> findByUserIdAndCategoryAndDateBetween(Long userId, Category category, 
        LocalDate startDate, LocalDate endDate, Pageable pageable);
    
    /**
     * Finds a specific expense by ID and user ID.
     * 
     * @param id The expense ID
     * @param userId The user ID who owns the expense
     * @return Optional containing the expense if found and owned by user
     */
    Optional<Expense> findByIdAndUserId(Long id, Long userId);
        
}
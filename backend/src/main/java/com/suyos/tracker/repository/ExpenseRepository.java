package com.suyos.tracker.repository;

import java.time.LocalDate;

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
     * Finds expenses by category with pagination.
     * 
     * @param category The expense category to filter by
     * @param pageable Pagination and sorting information
     * @return Page of expenses matching the specified category
     */
    Page<Expense> findByCategory(Category category, Pageable pageable);
    
    /**
     * Finds expenses within a date range with pagination.
     * 
     * @param startDate The start date (inclusive)
     * @param endDate The end date (inclusive)
     * @param pageable Pagination and sorting information
     * @return Page of expenses within the specified date range
     */
    Page<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    
    /**
     * Finds expenses by category and within a date range with pagination.
     * 
     * @param category The expense category to filter by
     * @param startDate The start date (inclusive)
     * @param endDate The end date (inclusive)
     * @param pageable Pagination and sorting information
     * @return Page of expenses matching both category and date range criteria
     */
    Page<Expense> findByCategoryAndDateBetween(Category category, LocalDate startDate, 
        LocalDate endDate, Pageable pageable);
        
}
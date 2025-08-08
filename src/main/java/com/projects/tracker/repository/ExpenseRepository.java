package com.projects.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.tracker.model.Expense;
import java.util.List;

/**
 * Repository interface for expense data access operations.
 * Extends JpaRepository to provide standard CRUD operations and
 * defines custom query methods for expense-specific data retrieval.
 *
 * @author Suyos Team
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    /**
     * Finds all expenses that occurred in a specific month.
     * 
     * @param month the month number (1-12) to filter expenses
     * @return List of expenses for the specified month
     */
    public List<Expense> findByMonth(int month);
    
}

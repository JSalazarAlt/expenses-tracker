package com.suyos.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suyos.tracker.model.Expense;

/**
 * Repository interface for Expense entity data access operations.
 * 
 * This interface extends JpaRepository to provide standard CRUD operations
 * and pagination support for Expense entities. Spring Data JPA automatically
 * generates the implementation at runtime.
 * 
 * Available operations include:
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
    // Additional custom query methods can be added here if needed
    // Spring Data JPA will automatically implement them based on method names
}

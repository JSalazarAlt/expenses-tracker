package com.projects.tracker.services;

import org.springframework.stereotype.Service;

import com.projects.tracker.model.Expense;
import com.projects.tracker.repository.ExpenseRepository;
import java.util.List;

/**
 * Service layer for managing expense operations.
 * Provides business logic for expense management including CRUD operations,
 * calculations, and data retrieval.
 *
 * @author Suyos Team
 * @version 1.0
 * @since 1.0
 */
@Service
public class ExpenseService {
    
    /**
     * Repository for expense data access.
     */
    private final ExpenseRepository expenseRepository;

    /**
     * Constructor for dependency injection of ExpenseRepository.
     * 
     * @param expenseRepository the repository for expense data access
     */
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /**
     * Retrieves all expenses from the database.
     * 
     * @return List of all expenses, empty list if no expenses exist
     */
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    /**
     * Saves a new expense or updates an existing one.
     * 
     * @param expense the expense entity to save
     * @throws IllegalArgumentException if expense is null
     */
    public void addExpense(Expense expense) {
        if (expense == null) {
            throw new IllegalArgumentException("Expense cannot be null");
        }
        expenseRepository.save(expense);
    }

    /**
     * Retrieves an expense by its unique identifier.
     * 
     * @param id the unique identifier of the expense
     * @return the expense if found, null otherwise
     * @throws IllegalArgumentException if id is null
     */
    public Expense getExpenseById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return expenseRepository.findById(id).orElse(null);
    }

    /**
     * Deletes an expense by its unique identifier.
     * 
     * @param id the unique identifier of the expense to delete
     * @throws IllegalArgumentException if id is null
     */
    public void deleteExpenseById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        expenseRepository.deleteById(id);
    }

    /**
     * Retrieves expenses for a specific month.
     * 
     * @param month the month (1-12) to filter expenses
     * @return List of expenses for the specified month
     * @throws IllegalArgumentException if month is not between 1 and 12
     */
    public List<Expense> getMonthlyExpenses(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        return expenseRepository.findByMonth(month);
    }

    /**
     * Calculates the total amount of all expenses.
     * 
     * @return the sum of all expense amounts, 0.0 if no expenses exist
     */
    public double getTotalExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        double total = 0.0;
        
        // Calculate sum of all expense amounts
        for (Expense expense : expenses) {
            if (expense != null) {
                total += expense.getAmount();
            }
        }
        
        return total;
    }

}

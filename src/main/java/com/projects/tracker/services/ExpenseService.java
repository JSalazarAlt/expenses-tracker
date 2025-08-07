package com.projects.tracker.services;

import org.springframework.stereotype.Service;

import com.projects.tracker.model.Expense;
import com.projects.tracker.repository.ExpenseRepository;
import java.util.List;

@Service
public class ExpenseService {
    
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public void addExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public void deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
    }

}

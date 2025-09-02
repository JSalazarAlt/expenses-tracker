package com.suyos.tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.suyos.tracker.dto.ExpenseDTO;
import com.suyos.tracker.mapper.ExpenseMapper;
import com.suyos.tracker.model.Expense;
import com.suyos.tracker.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
        
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    
    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.findAll()
            .stream()
            .map(expenseMapper::toDTO)
            .toList();
    }

    public ExpenseDTO getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found"));
        return expenseMapper.toDTO(expense);
    }

    public ExpenseDTO createExpense(ExpenseDTO expenseDTO) {
        Expense expense = expenseMapper.toEntity(expenseDTO);
        Expense saved = expenseRepository.save(expense);
        return expenseMapper.toDTO(saved);
    }
    
    public ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDTO) {
        // Validate that expense exists
        Expense existingExpense = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        
        // Update fields
        existingExpense.setExpenseDescription(expenseDTO.getExpenseDescription());
        existingExpense.setExpenseAmount(expenseDTO.getExpenseAmount());
        existingExpense.setExpenseDate(expenseDTO.getExpenseDate());
        existingExpense.setExpenseCategory(expenseDTO.getExpenseCategory());
        
        Expense updated = expenseRepository.save(existingExpense);
        return expenseMapper.toDTO(updated);
    }

    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new RuntimeException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }

}

package com.projects.tracker.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projects.tracker.model.Expense;
import com.projects.tracker.services.ExpenseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/expenses")
public class ExpenseController {
    
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping()
    public String getAllExpenses(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();
        model.addAttribute("expenses", expenses);
        return "expense-list";
    }

    @GetMapping("/new")
    public String showAddExpenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "expense-add";
    }

    @PostMapping("/new")
    public String addExpense(@ModelAttribute("expense") Expense expense) {
        expenseService.addExpense(expense);
        return "redirect:/expenses";
    }
    
}

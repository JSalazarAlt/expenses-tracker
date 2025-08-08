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

/**
 * Web controller for handling expense-related HTTP requests.
 * Manages the presentation layer for expense operations including
 * displaying expense lists, forms, and processing form submissions.
 *
 * @author Suyos Team
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/expenses")
public class ExpenseController {
    
    private final ExpenseService expenseService;

    /**
     * Constructor for dependency injection of ExpenseService.
     * @param expenseService the service layer for expense operations
     */
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * Displays the expense list page with all recorded expenses.
     * @param model the Spring MVC model for passing data to the view
     * @return the name of the expense list template
     */
    @GetMapping()
    public String getAllExpenses(Model model) {
        // Retrieve all expenses from the service layer
        List<Expense> expenses = expenseService.getAllExpenses();
        
        // Add expenses to model for template rendering
        model.addAttribute("expenses", expenses);
        
        return "expense-list";
    }

    /**
     * Displays the add expense form page.
     * @param model the Spring MVC model for passing data to the view
     * @return the name of the add expense template
     */
    @GetMapping("/new")
    public String showAddExpenseForm(Model model) {
        // Create empty expense object for form binding
        model.addAttribute("expense", new Expense());
        
        return "expense-add";
    }

    /**
     * Processes the add expense form submission.
     * @param expense the expense object populated from form data
     * @return redirect to the expense list page after successful creation
     */
    @PostMapping("/new")
    public String addExpense(@ModelAttribute("expense") Expense expense) {
        // Save the new expense through the service layer
        expenseService.addExpense(expense);
        
        // Redirect to expense list to show the newly added expense
        return "redirect:/expenses";
    }
    
}

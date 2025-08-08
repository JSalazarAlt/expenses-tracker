package com.projects.tracker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projects.tracker.model.Expense;
import com.projects.tracker.services.ExpenseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    /**
     * Service layer for expense operations.
     */
    private final ExpenseService expenseService;

    /**
     * Constructor for dependency injection of ExpenseService.
     * 
     * @param expenseService the service layer for expense operations
     */
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * Displays the expense list page with all recorded expenses.
     * 
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
     * 
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
     * 
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

    /**
     * Displays the yearly expenses chart page for a specific year.
     * Retrieves monthly expense totals and prepares data for Chart.js visualization.
     * 
     * @param model the Spring MVC model for passing data to the view
     * @param year the year for which to display expense chart data
     * @return the name of the expense chart template
     */
    @GetMapping("/chart/{year}")
    public String showYearlyExpensesChart(Model model, @PathVariable("year") int year) {
        
        // Get monthly expense totals for the specified year
        Map<Integer, Double> monthlyTotals = expenseService.getTotalExpensesByMonthAndYear(year);
        
        // Convert map values to list for Chart.js compatibility
        List<Double> monthlyArray = new ArrayList<>(monthlyTotals.values());

        // Add the monthly data array to model for chart rendering
        model.addAttribute("monthlyArray", monthlyArray);

        return "expense-chart";
    }

    @GetMapping("/api/{year}")
    @ResponseBody
    public List<Double> getMonthlyExpensesJson(@PathVariable int year) {
        Map<Integer, Double> monthlyTotals = expenseService.getTotalExpensesByMonthAndYear(year);

        List<Double> monthlyArray = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            monthlyArray.add(monthlyTotals.getOrDefault(month, 0.0));
        }
        return monthlyArray;
    }

    
    
}

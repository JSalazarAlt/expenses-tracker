package com.projects.tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing an expense record in the database.
 * Contains all necessary information about a financial expense including
 * description, amount, date, and category.
 *
 * @author Suyos Team
 * @version 1.0
 * @since 1.0
 */
@Entity(name = "expenses")
@Table(name = "expenses")
public class Expense {

    /**
     * Unique identifier for the expense record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Brief description of the expense.
     */
    @Column(name = "description")
    private String description;

    /**
     * Monetary amount of the expense.
     */
    @Column(name = "amount")
    private double amount;

    /**
     * Date when the expense occurred.
     */
    @Column(name = "date")
    private String date;

    /**
     * Category classification of the expense.
     */
    @Column(name = "category")
    private String category;
    
    /**
     * Default constructor required by JPA.
     */
    public Expense() { }

    /**
     * Constructor for creating a new expense with all required fields.
     *
     * @param description brief description of the expense
     * @param amount monetary amount of the expense
     * @param date date when the expense occurred
     * @param category category classification of the expense
     */
    public Expense(String description, double amount, String date, String category) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    /**
     * Gets the unique identifier of the expense.
     * 
     * @return the expense ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the expense.
     * Protected to prevent external modification of auto-generated IDs.
     * 
     * @param id the expense ID
     */
    protected void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the description of the expense.
     * 
     * @return the expense description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the expense.
     * 
     * @param description the expense description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the monetary amount of the expense.
     * 
     * @return the expense amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the monetary amount of the expense.
     * 
     * @param amount the expense amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the date when the expense occurred.
     * 
     * @return the expense date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date when the expense occurred.
     * 
     * @param date the expense date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the category classification of the expense.
     * 
     * @return the expense category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category classification of the expense.
     * 
     * @param category the expense category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    
    
}

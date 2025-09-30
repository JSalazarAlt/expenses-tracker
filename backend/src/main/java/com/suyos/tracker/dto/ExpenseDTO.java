package com.suyos.tracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.suyos.tracker.model.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for expense information.
 * 
 * This DTO is used to transfer expense data between the API layer and clients.
 * It provides a clean interface that decouples the internal entity structure
 * from the external API contract. JSON property annotations ensure consistent
 * field naming in API responses.
 * 
 * @author Joel Salazar
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDTO {
    
    /**
     * Unique identifier for the expense.
     * 
     * Null for new expenses being created, populated for existing expenses.
     */
    @JsonProperty("id")
    private Long id;

    /**
     * Descriptive text explaining what the expense was for.
     * 
     * Examples: "Grocery shopping", "Gas for car", "Monthly rent"
     */
    @JsonProperty("description")
    private String description;

    /**
     * Monetary amount of the expense.
     * 
     * Uses BigDecimal to maintain precision for financial calculations
     * and avoid floating-point rounding errors.
     */
    @JsonProperty("amount")
    private BigDecimal amount;

    /**
     * Date when the expense occurred.
     * 
     * Represents the actual date of the expense, not when it was recorded.
     */
    @JsonProperty("date")
    private LocalDate date;

    /**
     * Category classification for the expense.
     * 
     * Used for organizing and filtering expenses by type.
     */
    @JsonProperty("category")
    private Category category;

}
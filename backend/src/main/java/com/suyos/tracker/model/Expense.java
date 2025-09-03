package com.suyos.tracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing an expense record in the expense tracking system.
 * 
 * This class maps to the 'expenses' table in the database and contains
 * all the necessary fields to track individual expense transactions
 * including amount, date, category, and description.
 * 
 * @author Joel Salazar
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    /**
     * Unique identifier for the expense record.
     * 
     * Auto-generated using database identity strategy to ensure
     * unique primary key values for each expense entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long expenseId;

    /**
     * Descriptive text explaining what the expense was for.
     * 
     * Examples: "Grocery shopping", "Gas for car", "Monthly rent"
     */
    @Column(name = "expense_description")
    private String expenseDescription;

    /**
     * Monetary amount of the expense.
     * 
     * Uses BigDecimal for precise financial calculations and to avoid
     * floating-point precision issues. Minimum value is $0.01 to represent
     * actual monetary transactions.
     */
    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be at least $0.01")
    @Digits(integer = 15, fraction = 2, message = "Amount must have a maximum of 15 integer digits and 2 decimal places")
    @Column(name = "expense_amount", nullable = false, precision = 17, scale = 2)
    private BigDecimal expenseAmount;

    /**
     * Date when the expense occurred.
     * 
     * Uses LocalDate for date-only representation without time zone concerns.
     * This represents when the expense actually happened, not when it was recorded.
     */
    @NotNull(message = "Date is mandatory")
    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    /**
     * Category classification for the expense.
     * 
     * Stored as STRING enum to maintain readability in the database
     * and provide better data integrity than integer ordinals.
     */
    @NotNull(message = "Category is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(name = "expense_category", nullable = false)
    private Category expenseCategory;

    /**
     * Timestamp when the expense record was first created in the system.
     * 
     * Automatically set by Hibernate when the entity is first persisted.
     * This field is immutable after creation (updatable = false).
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the expense record was last modified.
     * 
     * Automatically updated by Hibernate whenever the entity is saved.
     * Useful for tracking when changes were made to expense records.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

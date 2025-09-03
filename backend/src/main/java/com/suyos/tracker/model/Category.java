package com.suyos.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeration of expense categories for classification purposes.
 * 
 * Each category has a human-readable description that can be used
 * in user interfaces while maintaining type safety and data integrity.
 * The enum values are stored as strings in the database for better readability.
 * 
 * @author Joel Salazar
 * @version 1.0
 * @since 2024-01-01
 */
@Getter
@AllArgsConstructor
public enum Category {
    
    /** Food and dining related expenses */
    FOOD("Food"),
    
    /** Housing expenses including rent, mortgage, property taxes */
    HOUSING("Housing"),
    
    /** Transportation costs including gas, public transit, rideshare */
    TRANSPORTATION("Transportation"),
    
    /** Utility bills such as electricity, water, internet, phone */
    UTILITIES("Utilities"),
    
    /** Entertainment expenses like movies, games, subscriptions */
    ENTERTAINMENT("Entertainment"),
    
    /** Medical and healthcare related costs */
    HEALTHCARE("Healthcare"),
    
    /** Educational expenses including courses, books, training */
    EDUCATION("Education"),
    
    /** Personal care items and services */
    PERSONAL_CARE("Personal Care"),
    
    /** Other expenses that don't fit into specific categories */
    MISCELLANEOUS("Miscellaneous");

    /**
     * Human-readable description of the category.
     * 
     * This field is immutable (final) to ensure category descriptions
     * cannot be modified after enum creation, maintaining data consistency.
     */
    private final String description;

}

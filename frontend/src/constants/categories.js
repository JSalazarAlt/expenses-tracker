/**
 * Array of expense category definitions with icons and labels.
 * 
 * Each category object contains the backend enum value, user-friendly label,
 * and an emoji icon for visual representation in the UI.
 * 
 * @constant {Array<Object>} CATEGORIES
 * @property {string} value - Backend enum value (matches Category.java)
 * @property {string} label - Human-readable category name
 * @property {string} icon - Emoji icon for visual representation
 * @author Joel Salazar
 * @since 1.0
 */
export const CATEGORIES = [
    { value: 'FOOD', label: 'Food', icon: '🍽️' }, // Food and dining expenses
    { value: 'HOUSING', label: 'Housing', icon: '🏠' }, // Rent, mortgage, property costs
    { value: 'TRANSPORTATION', label: 'Transportation', icon: '🚗' }, // Gas, public transit, rideshare
    { value: 'UTILITIES', label: 'Utilities', icon: '💡' }, // Electricity, water, internet, phone
    { value: 'ENTERTAINMENT', label: 'Entertainment', icon: '🎬' }, // Movies, games, subscriptions
    { value: 'HEALTHCARE', label: 'Healthcare', icon: '🏥' }, // Medical and health costs
    { value: 'EDUCATION', label: 'Education', icon: '📚' }, // Courses, books, training
    { value: 'PERSONAL_CARE', label: 'Personal Care', icon: '💄' }, // Personal care items and services
    { value: 'MISCELLANEOUS', label: 'Miscellaneous', icon: '📦' } // Other uncategorized expenses
];

/**
 * Retrieves the emoji icon for a given category value.
 * 
 * @function getCategoryIcon
 * @param {string} categoryValue - The category enum value (e.g., 'FOOD', 'HOUSING')
 * @returns {string} The emoji icon for the category, or default '📦' if not found
 * @author Joel Salazar
 * @since 1.0
 */
export const getCategoryIcon = (categoryValue) => {
    // Find category object by matching enum value
    const category = CATEGORIES.find(cat => cat.value === categoryValue);
    
    // Return icon or default if category not found
    return category?.icon || '📦';
};

/**
 * Retrieves the human-readable label for a given category value.
 * 
 * @function getCategoryLabel
 * @param {string} categoryValue - The category enum value (e.g., 'FOOD', 'HOUSING')
 * @returns {string} The human-readable label, or the original value if not found
 * @author Joel Salazar
 * @since 1.0
 */
export const getCategoryLabel = (categoryValue) => {
    // Find category object by matching enum value
    const category = CATEGORIES.find(cat => cat.value === categoryValue);
    
    // Return label or fallback to original value
    return category?.label || categoryValue;
};
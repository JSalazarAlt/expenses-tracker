import { useState } from 'react';
import './ExpenseForm.css';
import { CATEGORIES } from '../constants/categories';

/**
 * Expense form component for creating and editing expense records.
 * 
 * This component provides a reusable form that works for both creating new
 * expenses and editing existing ones. The form includes validation and
 * proper currency formatting for the amount field.
 * 
 * @component
 * @param {Object} props - Component props
 * @param {Object|null} props.expense - Existing expense data for editing (null for new expense)
 * @param {Function} props.onSubmit - Callback function called when form is submitted
 * @param {Function} props.onCancel - Callback function called when form is cancelled
 * @author Joel Salazar
 * @since 1.0
 */
function ExpenseForm({ expense, onSubmit, onCancel }) {
    /**
     * Form state containing all input field values.
     * Initialized with existing expense data for editing or empty values for new expense.
     */
    const [formData, setFormData] = useState({
        expenseDescription: expense?.expenseDescription || '', // Description text
        expenseAmount: expense?.expenseAmount || '', // Monetary amount
        expenseDate: expense?.expenseDate || '', // Date in YYYY-MM-DD format
        expenseCategory: expense?.expenseCategory || 'FOOD' // Default to FOOD category
    });



    /**
     * Handles input field changes and updates form state.
     * 
     * @param {Event} e - Input change event
     */
    const handleChange = (e) => {
        const { name, value } = e.target;
        
        // Update form state with new field value
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    /**
     * Handles form submission.
     * 
     * @param {Event} e - Form submit event
     */
    const handleSubmit = (e) => {
        e.preventDefault(); // Prevent default form submission
        onSubmit(formData); // Call parent callback with form data
    };

    return (
        <form className="expense-form" onSubmit={handleSubmit}>
            <h2>{expense ? 'Edit Expense' : 'Add New Expense'}</h2>
            
            <div className="form-group">
                <label htmlFor="expenseDescription">Description:</label>
                <input
                    type="text"
                    id="expenseDescription"
                    name="expenseDescription"
                    value={formData.expenseDescription}
                    onChange={handleChange}
                    required
                />
            </div>

            <div className="form-group">
                <label htmlFor="expenseAmount">Amount:</label>
                <div className="amount-input-wrapper">
                    <span className="currency-symbol">$</span>
                    <input
                        type="number"
                        id="expenseAmount"
                        name="expenseAmount"
                        value={formData.expenseAmount}
                        onChange={handleChange}
                        min="0.01"
                        step="0.01"
                        placeholder="0.00"
                        required
                    />
                </div>
            </div>

            <div className="form-group">
                <label htmlFor="expenseDate">Date:</label>
                <input
                    type="date"
                    id="expenseDate"
                    name="expenseDate"
                    value={formData.expenseDate}
                    onChange={handleChange}
                    min="2020-01-01" // Reasonable minimum date for expenses
                    max={new Date().toISOString().split('T')[0]} // Today's date as maximum
                    required
                />
            </div>

            <div className="form-group">
                <label htmlFor="expenseCategory">Category:</label>
                <select
                    id="expenseCategory"
                    name="expenseCategory"
                    value={formData.expenseCategory}
                    onChange={handleChange}
                    required
                >
                    {CATEGORIES.map(category => (
                        <option key={category.value} value={category.value}>
                            {category.icon} {category.label}
                        </option>
                    ))}
                </select>
            </div>

            <div className="form-buttons">
                <button type="submit" className="btn-primary">
                    {expense ? 'Update' : 'Create'}
                </button>
                <button type="button" className="btn-secondary" onClick={onCancel}>
                    Cancel
                </button>
            </div>
        </form>
    );
}

export default ExpenseForm;

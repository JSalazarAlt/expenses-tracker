import { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
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
        description: expense?.description || '', // Description text
        amount: expense?.amount || '', // Monetary amount
        date: expense?.date ? new Date(expense.date + 'T00:00:00') : new Date(), // Date object
        category: expense?.category || 'FOOD' // Default to FOOD category
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
     * Handles date picker changes.
     * 
     * @param {Date} date - Selected date
     */
    const handleDateChange = (date) => {
        setFormData(prev => ({
            ...prev,
            date: date
        }));
    };

    /**
     * Handles form submission.
     * 
     * @param {Event} e - Form submit event
     */
    const handleSubmit = (e) => {
        e.preventDefault(); // Prevent default form submission
        
        // Convert date to YYYY-MM-DD format for backend
        const submitData = {
            ...formData,
            date: formData.date.toISOString().split('T')[0]
        };
        
        onSubmit(submitData); // Call parent callback with form data
    };

    return (
        <form className="expense-form" onSubmit={handleSubmit}>
            <h2>{expense ? 'Edit Expense' : 'Add New Expense'}</h2>
            
            <div className="form-group">
                <label htmlFor="description">Description:</label>
                <input
                    type="text"
                    id="description"
                    name="description"
                    value={formData.description}
                    onChange={handleChange}
                    required
                />
            </div>

            <div className="form-group">
                <label htmlFor="amount">Amount:</label>
                <div className="amount-input-wrapper">
                    <span className="currency-symbol">$</span>
                    <input
                        type="number"
                        id="amount"
                        name="amount"
                        value={formData.amount}
                        onChange={handleChange}
                        min="0.01"
                        step="0.01"
                        placeholder="0.00"
                        required
                    />
                </div>
            </div>

            <div className="form-group">
                <label htmlFor="date">Date:</label>
                <DatePicker
                    selected={formData.date}
                    onChange={handleDateChange}
                    dateFormat="MMM dd, yyyy"
                    maxDate={new Date()}
                    minDate={new Date('2020-01-01')}
                    showPopperArrow={false}
                    className="date-picker-input"
                    calendarClassName="custom-calendar"
                    required
                />
            </div>

            <div className="form-group">
                <label htmlFor="category">Category:</label>
                <select
                    id="category"
                    name="category"
                    value={formData.category}
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

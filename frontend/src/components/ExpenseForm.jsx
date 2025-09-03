import { useState } from 'react';
import './ExpenseForm.css';
import { CATEGORIES } from '../constants/categories';

function ExpenseForm({ expense, onSubmit, onCancel }) {
    const [formData, setFormData] = useState({
        expenseDescription: expense?.expenseDescription || '',
        expenseAmount: expense?.expenseAmount || '',
        expenseDate: expense?.expenseDate || '',
        expenseCategory: expense?.expenseCategory || 'FOOD'
    });



    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit(formData);
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
                    min="2020-01-01"
                    max={new Date().toISOString().split('T')[0]}
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

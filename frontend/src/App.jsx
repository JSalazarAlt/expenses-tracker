import { useState } from 'react'
import ExpenseList from './components/ExpenseList'
import ExpenseForm from './components/ExpenseForm'
import { expenseAPI } from './services/api'
import './App.css'

/**
 * Main application component managing expense tracker state and navigation.
 * 
 * This component orchestrates the entire expense tracking application,
 * managing the state for showing either the expense list or the expense form,
 * and handling all CRUD operations through the API service.
 * 
 * @component
 * @author Joel Salazar
 * @since 1.0
 */
function App() {
    /** Controls whether to show the form or the list view */
    const [showForm, setShowForm] = useState(false)
    
    /** Holds the expense being edited (null for new expense creation) */
    const [editingExpense, setEditingExpense] = useState(null)

    /**
     * Handles the "Add Expense" action by showing the form in create mode.
     */
    const handleAddExpense = () => {
        setEditingExpense(null) // Clear any existing expense data
        setShowForm(true) // Show the form
    }

    /**
     * Handles the "Edit Expense" action by showing the form in edit mode.
     * 
     * @param {Object} expense - The expense object to edit
     */
    const handleEditExpense = (expense) => {
        setEditingExpense(expense) // Set the expense to edit
        setShowForm(true) // Show the form
    }

    /**
     * Handles form submission for both create and update operations.
     * 
     * @param {Object} formData - The form data to save
     */
    const handleFormSubmit = async (formData) => {
        try {
            if (editingExpense) {
                // Update existing expense
                await expenseAPI.update(editingExpense.expenseId, formData)
            } else {
                // Create new expense
                await expenseAPI.create(formData)
            }
            
            // Return to list view after successful save
            setShowForm(false)
            setEditingExpense(null)
        } catch (error) {
            console.error('Error saving expense:', error)
            // TODO: Show user-friendly error message
        }
    }

    /**
     * Handles form cancellation by returning to the list view.
     */
    const handleFormCancel = () => {
        setShowForm(false) // Hide the form
        setEditingExpense(null) // Clear editing state
    }

    // Conditional rendering: show form or list based on current state
    if (showForm) {
        return (
            <ExpenseForm 
                expense={editingExpense} // Pass expense data for editing (null for new)
                onSubmit={handleFormSubmit} // Handle form submission
                onCancel={handleFormCancel} // Handle form cancellation
            />
        )
    }

    // Default view: show expense list
    return (
        <ExpenseList 
            onAddExpense={handleAddExpense} // Handle add expense action
            onEditExpense={handleEditExpense} // Handle edit expense action
        />
    )
}

export default App

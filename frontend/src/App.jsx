import { useReducer, useCallback } from 'react'
import ExpenseList from './components/ExpenseList'
import ExpenseForm from './components/ExpenseForm'
import { expenseAPI } from './services/api'
import './App.css'

// Action types for state management
const ACTIONS = {
    SHOW_ADD_FORM: 'SHOW_ADD_FORM',
    SHOW_EDIT_FORM: 'SHOW_EDIT_FORM',
    HIDE_FORM: 'HIDE_FORM'
}

// Reducer for managing app state
const appReducer = (state, action) => {
    switch (action.type) {
        case ACTIONS.SHOW_ADD_FORM:
            return { showForm: true, editingExpense: null }
        case ACTIONS.SHOW_EDIT_FORM:
            return { showForm: true, editingExpense: action.payload }
        case ACTIONS.HIDE_FORM:
            return { showForm: false, editingExpense: null }
        default:
            return state
    }
}

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
    const [{ showForm, editingExpense }, dispatch] = useReducer(appReducer, {
        showForm: false,
        editingExpense: null
    })

    /**
     * Handles the "Add Expense" action by showing the form in create mode.
     */
    const handleAddExpense = useCallback(() => {
        dispatch({ type: ACTIONS.SHOW_ADD_FORM })
    }, [])

    /**
     * Handles the "Edit Expense" action by showing the form in edit mode.
     */
    const handleEditExpense = useCallback((expense) => {
        dispatch({ type: ACTIONS.SHOW_EDIT_FORM, payload: expense })
    }, [])

    /**
     * Handles form cancellation by returning to the list view.
     */
    const handleFormCancel = useCallback(() => {
        dispatch({ type: ACTIONS.HIDE_FORM })
    }, [])

    /**
     * Handles form submission for both create and update operations.
     */
    const handleFormSubmit = useCallback(async (formData) => {
        try {
            if (editingExpense) {
                await expenseAPI.update(editingExpense.expenseId, formData)
            } else {
                await expenseAPI.create(formData)
            }
            dispatch({ type: ACTIONS.HIDE_FORM })
        } catch (error) {
            console.error('Error saving expense:', error)
            // TODO: Show user-friendly error message
        }
    }, [editingExpense])

    return showForm ? (
        <ExpenseForm 
            expense={editingExpense}
            onSubmit={handleFormSubmit}
            onCancel={handleFormCancel}
        />
    ) : (
        <ExpenseList 
            onAddExpense={handleAddExpense}
            onEditExpense={handleEditExpense}
        />
    )
}

export default App

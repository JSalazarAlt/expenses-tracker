import { useReducer, useCallback, useEffect, useState } from 'react'
import ExpenseList from './components/ExpenseList'
import ExpenseForm from './components/ExpenseForm'
import Login from './components/Login'
import Register from './components/Register'
import { expenseAPI, authAPI } from './services/api'
import './App.css'

// Action types for state management
const ACTIONS = {
    SHOW_ADD_FORM: 'SHOW_ADD_FORM',
    SHOW_EDIT_FORM: 'SHOW_EDIT_FORM',
    HIDE_FORM: 'HIDE_FORM'
}

// Auth view types
const AUTH_VIEWS = {
    LOGIN: 'LOGIN',
    REGISTER: 'REGISTER'
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
 * Main application component managing expense tracker state, navigation, and authentication.
 * 
 * This component orchestrates the entire expense tracking application,
 * managing authentication state, showing login/register screens for unauthenticated users,
 * and the main expense management interface for authenticated users.
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
    
    const [user, setUser] = useState(null)
    const [authView, setAuthView] = useState(AUTH_VIEWS.LOGIN)
    const [loading, setLoading] = useState(true)
    
    // Check for existing authentication on app load
    useEffect(() => {
        const token = localStorage.getItem('token')
        const userData = localStorage.getItem('user')
        
        if (token && userData) {
            try {
                const parsedUser = JSON.parse(userData)
                setUser(parsedUser)
                authAPI.setAuthToken(token)
            } catch (error) {
                console.error('Error parsing stored user data:', error)
                localStorage.removeItem('token')
                localStorage.removeItem('user')
            }
        }
        
        setLoading(false)
    }, [])

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
                await expenseAPI.update(editingExpense.id, formData)
            } else {
                await expenseAPI.create(formData)
            }
            dispatch({ type: ACTIONS.HIDE_FORM })
        } catch (error) {
            console.error('Error saving expense:', error)
            // TODO: Show user-friendly error message
        }
    }, [editingExpense])
    
    /**
     * Handles successful login/registration.
     */
    const handleAuthSuccess = useCallback((userData) => {
        setUser(userData)
    }, [])
    
    /**
     * Handles user logout.
     */
    const handleLogout = useCallback(() => {
        authAPI.logout()
        setUser(null)
        dispatch({ type: ACTIONS.HIDE_FORM })
    }, [])
    
    /**
     * Switches between login and register views.
     */
    const handleSwitchAuthView = useCallback((view) => {
        setAuthView(view)
    }, [])

    // Show loading spinner while checking authentication
    if (loading) {
        return (
            <div style={{ 
                display: 'flex', 
                justifyContent: 'center', 
                alignItems: 'center', 
                height: '100vh',
                fontSize: '18px',
                color: 'var(--gray-600)'
            }}>
                Loading...
            </div>
        )
    }
    
    // Show authentication screens if user is not logged in
    if (!user) {
        return authView === AUTH_VIEWS.LOGIN ? (
            <Login 
                onLoginSuccess={handleAuthSuccess}
                onSwitchToRegister={() => handleSwitchAuthView(AUTH_VIEWS.REGISTER)}
            />
        ) : (
            <Register 
                onRegisterSuccess={handleAuthSuccess}
                onSwitchToLogin={() => handleSwitchAuthView(AUTH_VIEWS.LOGIN)}
            />
        )
    }
    
    // Show main application if user is authenticated
    return (
        <div>
            {/* Header with user info and logout */}
            <header style={{
                background: 'white',
                padding: 'var(--space-md) var(--space-lg)',
                borderBottom: '1px solid var(--gray-200)',
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center'
            }}>
                <h1 style={{ margin: 0, color: 'var(--gray-900)' }}>Expense Tracker</h1>
                <div style={{ display: 'flex', alignItems: 'center', gap: 'var(--space-md)' }}>
                    <span style={{ color: 'var(--gray-600)' }}>Welcome, {user.firstName}!</span>
                    <button 
                        onClick={handleLogout}
                        style={{
                            padding: 'var(--space-sm) var(--space-md)',
                            background: 'var(--gray-100)',
                            border: '1px solid var(--gray-300)',
                            borderRadius: 'var(--radius-md)',
                            cursor: 'pointer',
                            color: 'var(--gray-700)'
                        }}
                    >
                        Logout
                    </button>
                </div>
            </header>
            
            {/* Main content */}
            {showForm ? (
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
            )}
        </div>
    )
}

export default App

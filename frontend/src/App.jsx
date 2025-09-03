import { useState } from 'react'
import ExpenseList from './components/ExpenseList'
import ExpenseForm from './components/ExpenseForm'
import { expenseAPI } from './services/api'
import './App.css'

function App() {
    const [showForm, setShowForm] = useState(false)
    const [editingExpense, setEditingExpense] = useState(null)

    const handleAddExpense = () => {
        setEditingExpense(null)
        setShowForm(true)
    }

    const handleEditExpense = (expense) => {
        setEditingExpense(expense)
        setShowForm(true)
    }

    const handleFormSubmit = async (formData) => {
        try {
            if (editingExpense) {
                await expenseAPI.update(editingExpense.expenseId, formData)
            } else {
                await expenseAPI.create(formData)
            }
            setShowForm(false)
            setEditingExpense(null)
        } catch (error) {
            console.error('Error saving expense:', error)
        }
    }

    const handleFormCancel = () => {
        setShowForm(false)
        setEditingExpense(null)
    }

    if (showForm) {
        return (
            <ExpenseForm 
                expense={editingExpense}
                onSubmit={handleFormSubmit}
                onCancel={handleFormCancel}
            />
        )
    }

    return (
        <ExpenseList 
            onAddExpense={handleAddExpense}
            onEditExpense={handleEditExpense}
        />
    )
}

export default App

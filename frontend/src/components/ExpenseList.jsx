import { useEffect, useState } from 'react';
import './ExpenseList.css';
import { expenseAPI } from '../services/api';
import { getCategoryIcon } from '../constants/categories';

/**
 * Expense list component displaying paginated expense data in a table.
 * 
 * This component fetches and displays expenses with server-side pagination,
 * sorting by date (newest first). It includes controls for page size selection
 * and provides edit/delete actions for each expense.
 * 
 * @component
 * @param {Object} props - Component props
 * @param {Function} props.onAddExpense - Callback function for adding new expense
 * @param {Function} props.onEditExpense - Callback function for editing existing expense
 * @author Joel Salazar
 * @since 1.0
 */
function ExpenseList({ onAddExpense, onEditExpense }) {
    /** Current page expense data */
    const [data, setData] = useState([]);
    
    /** Loading state for API requests */
    const [loading, setLoading] = useState(true);
    
    /** Error state for failed API requests */
    const [error, setError] = useState(null);
    
    /** Current page number (zero-based) */
    const [currentPage, setCurrentPage] = useState(0);
    
    /** Number of items to display per page */
    const [itemsPerPage, setItemsPerPage] = useState(10);
    
    /** Total number of pages available */
    const [totalPages, setTotalPages] = useState(0);
    
    /** Total number of expense records */
    const [totalElements, setTotalElements] = useState(0);

    /**
     * Effect hook to fetch expense data when page or size changes.
     * Automatically refetches data when currentPage or itemsPerPage changes.
     */
    useEffect(() => {
        const fetchData = async () => {
            setLoading(true); // Show loading state
            try {
                // Fetch paginated data from API
                const response = await expenseAPI.getPaginated(currentPage, itemsPerPage);
                
                // Update state with response data
                setData(response.content);
                setTotalPages(response.totalPages);
                setTotalElements(response.totalElements);
            } catch (err) {
                setError(err); // Store error for display
            } finally {
                setLoading(false); // Hide loading state
            }
        };
        fetchData();
    }, [currentPage, itemsPerPage]); // Re-run when pagination changes

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    if (error) {
        return <div className="error">Error: {error.message}</div>;
    }



    /**
     * Handles expense deletion and refreshes the current page.
     * 
     * @param {number} id - The ID of the expense to delete
     */
    const handleDelete = async (id) => {
        try {
            // Delete expense via API
            await expenseAPI.delete(id);
            
            // Refresh current page data to reflect deletion
            const response = await expenseAPI.getPaginated(currentPage, itemsPerPage);
            setData(response.content);
            setTotalPages(response.totalPages);
            setTotalElements(response.totalElements);
        } catch (err) {
            setError(err); // Handle deletion errors
        }
    };

    return (
        <div className="expense-list">
            <div className="list-header">
                <div className="header-content">
                    <h1 className="page-title">Expense Tracker</h1>
                    <p className="page-subtitle">Manage your expenses efficiently</p>
                </div>
                <button className="btn-add" onClick={onAddExpense}>
                    <span className="btn-icon">+</span>
                    Add Expense
                </button>
            </div>
            
            <div className="list-controls">
                <label>
                    Show: 
                    <select value={itemsPerPage} onChange={(e) => {
                        setItemsPerPage(Number(e.target.value)); // Update page size
                        setCurrentPage(0); // Reset to first page
                    }}>
                        <option value={10}>10 per page</option>
                        <option value={25}>25 per page</option>
                    </select>
                </label>
            </div>

            <table className="expense-table">
                <thead>
                    <tr>
                        <th>Description</th>
                        <th>Category</th>
                        <th>Amount</th>
                        <th>Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map(expense => (
                        <tr key={expense.expenseId}>
                            <td>{expense.expenseDescription}</td>
                            <td>{getCategoryIcon(expense.expenseCategory)} {expense.expenseCategory}</td>
                            <td className="amount">${parseFloat(expense.expenseAmount).toFixed(2)}</td>
                            <td className="date">{expense.expenseDate}</td>
                            <td className="actions">
                                <button className="btn-edit" onClick={() => onEditExpense(expense)}>Edit</button>
                                <button className="btn-delete" onClick={() => handleDelete(expense.expenseId)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            <div className="pagination">
                <button 
                    onClick={() => setCurrentPage(prev => Math.max(prev - 1, 0))} // Go to previous page
                    disabled={currentPage === 0} // Disable on first page
                >
                    Previous
                </button>
                <span>Page {currentPage + 1} of {totalPages} ({totalElements} total)</span>
                <button 
                    onClick={() => setCurrentPage(prev => Math.min(prev + 1, totalPages - 1))} // Go to next page
                    disabled={currentPage >= totalPages - 1} // Disable on last page
                >
                    Next
                </button>
            </div>
        </div>
    );
};

export default ExpenseList;
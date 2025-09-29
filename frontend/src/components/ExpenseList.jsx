import { useEffect, useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import './ExpenseList.css';
import { expenseAPI } from '../services/api';
import { getCategoryIcon, CATEGORIES } from '../constants/categories';

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
    
    /** Filter by category */
    const [filterCategory, setFilterCategory] = useState('');
    
    /** Filter by start date */
    const [filterStartDate, setFilterStartDate] = useState(null);
    
    /** Filter by end date */
    const [filterEndDate, setFilterEndDate] = useState(null);

    /**
     * Effect hook to fetch expense data when pagination or filters change.
     * Automatically refetches data when currentPage, itemsPerPage, or any filter changes.
     */
    useEffect(() => {
        const fetchData = async () => {
            setLoading(true); // Show loading state
            try {
                // Fetch paginated data from API with filters
                const response = await expenseAPI.getPaginated(
                    currentPage, 
                    itemsPerPage, 
                    "date", 
                    "desc", 
                    filterCategory || null, 
                    filterStartDate ? filterStartDate.toISOString().split('T')[0] : null, 
                    filterEndDate ? filterEndDate.toISOString().split('T')[0] : null
                );
                
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
    }, [currentPage, itemsPerPage, filterCategory, filterStartDate, filterEndDate]); // Re-run when pagination or filters change

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    if (error) {
        return <div className="error">Error: {error.message}</div>;
    }



    /**
     * Handles expense deletion and updates local state.
     * 
     * @param {number} id - The ID of the expense to delete
     */
    const handleDelete = async (id) => {
        try {
            // Delete expense via API
            await expenseAPI.delete(id);
            
            // Update local state instead of refetching
            setData(prev => prev.filter(expense => expense.id !== id));
            setTotalElements(prev => prev - 1);
            
            // Recalculate total pages
            const newTotalElements = totalElements - 1;
            const newTotalPages = Math.ceil(newTotalElements / itemsPerPage);
            setTotalPages(newTotalPages);
            
            // If current page becomes empty, go to previous page
            if (data.length === 1 && currentPage > 0) {
                setCurrentPage(prev => prev - 1);
            }
        } catch (err) {
            setError(err);
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
                
                <label>
                    Category:
                    <select value={filterCategory} onChange={(e) => {
                        setFilterCategory(e.target.value);
                        setCurrentPage(0); // Reset to first page
                    }}>
                        <option value="">All Categories</option>
                        {CATEGORIES.map(category => (
                            <option key={category.value} value={category.value}>
                                {category.icon} {category.label}
                            </option>
                        ))}
                    </select>
                </label>
                
                <label>
                    Start Date:
                    <DatePicker
                        selected={filterStartDate}
                        onChange={(date) => {
                            setFilterStartDate(date);
                            if (date && filterEndDate && date > filterEndDate) {
                                setFilterEndDate(date);
                            }
                            setCurrentPage(0);
                        }}
                        dateFormat="MMM dd, yyyy"
                        placeholderText="Select start date"
                        maxDate={new Date()}
                        showPopperArrow={false}
                        className="filter-date-picker"
                        calendarClassName="custom-calendar"
                        isClearable
                    />
                </label>
                
                <label>
                    End Date:
                    <DatePicker
                        selected={filterEndDate}
                        onChange={(date) => {
                            setFilterEndDate(date);
                            if (date && filterStartDate && date < filterStartDate) {
                                setFilterStartDate(date);
                            }
                            setCurrentPage(0);
                        }}
                        dateFormat="MMM dd, yyyy"
                        placeholderText="Select end date"
                        maxDate={new Date()}
                        showPopperArrow={false}
                        className="filter-date-picker"
                        calendarClassName="custom-calendar"
                        isClearable
                    />
                </label>
                
                <button 
                    className="btn-clear-filters" 
                    onClick={() => {
                        setFilterCategory('');
                        setFilterStartDate(null);
                        setFilterEndDate(null);
                        setCurrentPage(0);
                    }}
                >
                    Clear Filters
                </button>
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
                        <tr key={expense.id}>
                            <td>{expense.description}</td>
                            <td>{getCategoryIcon(expense.category)} {expense.category}</td>
                            <td className="amount">${parseFloat(expense.amount).toFixed(2)}</td>
                            <td className="date">{expense.date}</td>
                            <td className="actions">
                                <button className="btn-edit" onClick={() => onEditExpense(expense)}>✏️ Edit</button>
                                <button className="btn-delete" onClick={() => handleDelete(expense.id)}>🗑️ Delete</button>
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
import { useEffect, useState } from 'react';
import './ExpenseList.css';
import { expenseAPI } from '../services/api';
import { getCategoryIcon } from '../constants/categories';

function ExpenseList({ onAddExpense, onEditExpense }) {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentPage, setCurrentPage] = useState(0);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [totalPages, setTotalPages] = useState(0);
    const [totalElements, setTotalElements] = useState(0);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                const response = await expenseAPI.getPaginated(currentPage, itemsPerPage);
                setData(response.content);
                setTotalPages(response.totalPages);
                setTotalElements(response.totalElements);
            } catch (err) {
                setError(err);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [currentPage, itemsPerPage]);

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    if (error) {
        return <div className="error">Error: {error.message}</div>;
    }



    const handleDelete = async (id) => {
        try {
            await expenseAPI.delete(id);
            // Refresh current page data
            const response = await expenseAPI.getPaginated(currentPage, itemsPerPage);
            setData(response.content);
            setTotalPages(response.totalPages);
            setTotalElements(response.totalElements);
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
                        setItemsPerPage(Number(e.target.value));
                        setCurrentPage(0);
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
                    onClick={() => setCurrentPage(prev => Math.max(prev - 1, 0))}
                    disabled={currentPage === 0}
                >
                    Previous
                </button>
                <span>Page {currentPage + 1} of {totalPages} ({totalElements} total)</span>
                <button 
                    onClick={() => setCurrentPage(prev => Math.min(prev + 1, totalPages - 1))}
                    disabled={currentPage >= totalPages - 1}
                >
                    Next
                </button>
            </div>
        </div>
    );
};

export default ExpenseList;
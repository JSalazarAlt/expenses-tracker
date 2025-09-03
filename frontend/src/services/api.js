/**
 * Base URL for the expense tracker API endpoints.
 * Points to the Spring Boot backend server.
 * 
 * @constant {string}
 * @author Joel Salazar
 * @since 1.0
 */
const API_BASE_URL = 'http://localhost:8080/api';

/**
 * API service object containing all expense-related HTTP operations.
 * 
 * This service provides a centralized interface for communicating with
 * the backend REST API. All methods return Promises that resolve to
 * the parsed JSON response data.
 * 
 * @namespace expenseAPI
 * @author Joel Salazar
 * @since 1.0
 */
export const expenseAPI = {
    /**
     * Retrieves all expenses without pagination.
     * 
     * @function getAll
     * @returns {Promise<Array>} Promise resolving to array of expense objects
     * @since 1.0
     */
    getAll: () => fetch(`${API_BASE_URL}/expenses`).then(res => res.json()),
    
    /**
     * Retrieves expenses with server-side pagination.
     * 
     * @function getPaginated
     * @param {number} [page=0] - Zero-based page number
     * @param {number} [size=10] - Number of records per page
     * @returns {Promise<Object>} Promise resolving to paginated response with content and metadata
     * @since 1.0
     */
    getPaginated: (page = 0, size = 10) => 
        fetch(`${API_BASE_URL}/expenses?page=${page}&size=${size}`).then(res => res.json()),
    
    /**
     * Creates a new expense record.
     * 
     * @function create
     * @param {Object} expense - Expense data object
     * @param {string} expense.expenseDescription - Description of the expense
     * @param {number} expense.expenseAmount - Monetary amount
     * @param {string} expense.expenseDate - Date in YYYY-MM-DD format
     * @param {string} expense.expenseCategory - Category enum value
     * @returns {Promise<Object>} Promise resolving to created expense with generated ID
     * @since 1.0
     */
    create: (expense) => 
        fetch(`${API_BASE_URL}/expenses`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(expense) // Convert JS object to JSON string
        }).then(res => res.json()),
    
    /**
     * Updates an existing expense record.
     * 
     * @function update
     * @param {number} id - Unique identifier of the expense to update
     * @param {Object} expense - Updated expense data
     * @returns {Promise<Object>} Promise resolving to updated expense data
     * @throws {Error} If expense with given ID doesn't exist
     * @since 1.0
     */
    update: (id, expense) =>
        fetch(`${API_BASE_URL}/expenses/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(expense) // Convert updated data to JSON
        }).then(res => res.json()),
    
    /**
     * Deletes an expense record by ID.
     * 
     * @function delete
     * @param {number} id - Unique identifier of the expense to delete
     * @returns {Promise<Response>} Promise resolving to fetch Response object
     * @throws {Error} If expense with given ID doesn't exist
     * @since 1.0
     */
    delete: (id) =>
        fetch(`${API_BASE_URL}/expenses/${id}`, { method: 'DELETE' }) // No body needed for DELETE
};
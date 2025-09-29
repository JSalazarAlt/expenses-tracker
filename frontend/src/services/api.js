import axios from 'axios';

/**
 * Base URL for the expense tracker API endpoints.
 * Points to the Spring Boot backend server.
 * 
 * @constant {string}
 * @author Joel Salazar
 * @since 1.0
 */
const API_BASE_URL = 'http://localhost:8080/api';

// Create axios instance with base configuration
const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

/**
 * API service object containing all expense-related HTTP operations.
 * 
 * This service provides a centralized interface for communicating with
 * the backend REST API. All methods return Promises that resolve to
 * the response data.
 * 
 * @namespace expenseAPI
 * @author Joel Salazar
 * @since 1.0
 */
export const expenseAPI = {
    /**
     * Retrieves expenses with server-side pagination.
     * 
     * @function getPaginated
     * @param {number} [page=0] - Zero-based page number
     * @param {number} [size=10] - Number of records per page
     * @param {string} [sortBy="date"] - Sort field
     * @param {string} [sortDir="desc"] - Sort direction (asc/desc)
     * @param {string} [category=null] - Optional category filter
     * @param {string} [startDate=null] - Optional start date filter (YYYY-MM-DD)
     * @param {string} [endDate=null] - Optional end date filter (YYYY-MM-DD)
     * @returns {Promise<Object>} Promise resolving to paginated response with content and metadata
     * @since 1.0
     */
    getPaginated: async (page = 0, size = 10, sortBy = "date", sortDir = "desc", 
        category = null, startDate = null, endDate = null) => {
        const params = { page, size, sortBy, sortDir };
        if (category) params.category = category;
        if (startDate) params.startDate = startDate;
        if (endDate) params.endDate = endDate;
        
        const response = await apiClient.get('/expenses', { params });
        return response.data;
    },

    /**
     * Creates a new expense record.
     * 
     * @function create
     * @param {Object} expense - Expense data object
     * @param {string} expense.description - Description of the expense
     * @param {number} expense.amount - Monetary amount
     * @param {string} expense.date - Date in YYYY-MM-DD format
     * @param {string} expense.category - Category enum value
     * @returns {Promise<Object>} Promise resolving to created expense with generated ID
     * @since 1.0
     */
    create: async (expense) => {
        const response = await apiClient.post('/expenses', expense);
        return response.data;
    },
    
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
    update: async (id, expense) => {
        const response = await apiClient.put(`/expenses/${id}`, expense);
        return response.data;
    },
    
    /**
     * Deletes an expense record by ID.
     * 
     * @function delete
     * @param {number} id - Unique identifier of the expense to delete
     * @returns {Promise<void>} Promise that resolves when deletion is complete
     * @throws {Error} If expense with given ID doesn't exist
     * @since 1.0
     */
    delete: async (id) => {
        await apiClient.delete(`/expenses/${id}`);
    }
};
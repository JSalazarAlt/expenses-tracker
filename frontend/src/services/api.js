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

// Add request interceptor to include JWT token
apiClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Add response interceptor to handle authentication errors
apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            // Only reload if this is an authenticated request (has token)
            // Don't reload during login/register attempts
            const token = localStorage.getItem('token');
            if (token) {
                // Clear stored auth data on unauthorized response
                localStorage.removeItem('token');
                localStorage.removeItem('user');
                // Redirect to login or trigger auth state update
                window.location.reload();
            }
        }
        return Promise.reject(error);
    }
);

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

/**
 * Authentication API service for user login, registration, and JWT management.
 * 
 * @namespace authAPI
 * @author Joel Salazar
 * @since 1.0
 */
export const authAPI = {
    /**
     * Authenticates user login and returns JWT token.
     * 
     * @function login
     * @param {Object} credentials - User login credentials
     * @param {string} credentials.email - User's email address
     * @param {string} credentials.password - User's password
     * @returns {Promise<Object>} Promise resolving to authentication response with token and user info
     * @since 1.0
     */
    login: async (credentials) => {
        const response = await apiClient.post('/users/login', credentials);
        return response.data;
    },

    /**
     * Registers a new user account.
     * 
     * @function register
     * @param {Object} userData - New user registration data
     * @param {string} userData.email - User's email address
     * @param {string} userData.password - User's password
     * @param {string} userData.username - User's chosen username
     * @param {string} userData.firstName - User's first name
     * @param {string} userData.lastName - User's last name
     * @param {boolean} userData.termsAccepted - Terms acceptance flag
     * @param {boolean} userData.privacyPolicyAccepted - Privacy policy acceptance flag
     * @returns {Promise<Object>} Promise resolving to created user profile
     * @since 1.0
     */
    register: async (userData) => {
        const response = await apiClient.post('/users/register', userData);
        return response.data;
    },

    /**
     * Sets the authorization token for API requests.
     * 
     * @function setAuthToken
     * @param {string} token - JWT token to set
     * @since 1.0
     */
    setAuthToken: (token) => {
        if (token) {
            apiClient.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        } else {
            delete apiClient.defaults.headers.common['Authorization'];
        }
    },

    /**
     * Logs out the current user by clearing stored authentication data.
     * 
     * @function logout
     * @since 1.0
     */
    logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        delete apiClient.defaults.headers.common['Authorization'];
    }
};
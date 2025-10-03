import { useState } from 'react'
import { authAPI } from '../services/api'
import './Auth.css'

/**
 * Registration component for new user signup.
 * 
 * Provides a form for users to create a new account with required information.
 * Handles user registration and automatically logs them in after successful signup.
 * 
 * @component
 * @param {Function} onRegisterSuccess - Callback function called after successful registration
 * @param {Function} onSwitchToLogin - Callback to switch to login form
 * @author Joel Salazar
 * @since 1.0
 */
function Register({ onRegisterSuccess, onSwitchToLogin }) {
    const [formData, setFormData] = useState({
        email: '',
        password: '',
        confirmPassword: '',
        username: '',
        firstName: '',
        lastName: '',
        termsAccepted: false,
        privacyPolicyAccepted: false
    })
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState('')

    /**
     * Handles input field changes and updates form state.
     */
    const handleChange = (e) => {
        const { name, value, type, checked } = e.target
        setFormData(prev => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value
        }))
        // Clear error when user starts typing
        if (error) setError('')
    }

    /**
     * Validates form data before submission.
     */
    const validateForm = () => {
        if (formData.password !== formData.confirmPassword) {
            setError('Passwords do not match')
            return false
        }
        if (formData.password.length < 6) {
            setError('Password must be at least 6 characters long')
            return false
        }
        if (!formData.termsAccepted) {
            setError('You must accept the terms of service')
            return false
        }
        if (!formData.privacyPolicyAccepted) {
            setError('You must accept the privacy policy')
            return false
        }
        return true
    }

    /**
     * Handles form submission and user registration.
     */
    const handleSubmit = async (e) => {
        e.preventDefault()
        
        if (!validateForm()) {
            return
        }

        setLoading(true)
        setError('')

        try {
            // Remove confirmPassword from submission data
            const { confirmPassword, ...registrationData } = formData
            
            const response = await authAPI.register(registrationData)
            
            // Auto-login after successful registration
            const loginResponse = await authAPI.login({
                email: formData.email,
                password: formData.password
            })
            
            // Store JWT token and user info
            localStorage.setItem('token', loginResponse.accessToken)
            localStorage.setItem('user', JSON.stringify(loginResponse.user))
            
            // Set default authorization header for future requests
            authAPI.setAuthToken(loginResponse.accessToken)
            
            onRegisterSuccess(loginResponse.user)
        } catch (error) {
            console.error('Registration error:', error)
            setError(error.response?.data || 'Registration failed. Please try again.')
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className="auth-container">
            <form className="auth-form" onSubmit={handleSubmit}>
                <div className="auth-header">
                    <h2>Create Account</h2>
                    <p>Join us to start tracking your expenses</p>
                </div>

                {error && (
                    <div className="error-message">
                        {error}
                    </div>
                )}

                <div className="form-row">
                    <div className="form-group">
                        <label htmlFor="firstName">First Name</label>
                        <input
                            type="text"
                            id="firstName"
                            name="firstName"
                            value={formData.firstName}
                            onChange={handleChange}
                            required
                            placeholder="Enter your first name"
                            disabled={loading}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="lastName">Last Name</label>
                        <input
                            type="text"
                            id="lastName"
                            name="lastName"
                            value={formData.lastName}
                            onChange={handleChange}
                            required
                            placeholder="Enter your last name"
                            disabled={loading}
                        />
                    </div>
                </div>

                <div className="form-group">
                    <label htmlFor="username">Username</label>
                    <input
                        type="text"
                        id="username"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        required
                        placeholder="Choose a username"
                        disabled={loading}
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="email">Email Address</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                        placeholder="Enter your email"
                        disabled={loading}
                    />
                </div>

                <div className="form-row">
                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                            placeholder="Create a password"
                            disabled={loading}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="confirmPassword">Confirm Password</label>
                        <input
                            type="password"
                            id="confirmPassword"
                            name="confirmPassword"
                            value={formData.confirmPassword}
                            onChange={handleChange}
                            required
                            placeholder="Confirm your password"
                            disabled={loading}
                        />
                    </div>
                </div>

                <div className="checkbox-group">
                    <label className="checkbox-label">
                        <input
                            type="checkbox"
                            name="termsAccepted"
                            checked={formData.termsAccepted}
                            onChange={handleChange}
                            required
                            disabled={loading}
                        />
                        <span className="checkmark"></span>
                        I accept the Terms of Service
                    </label>
                </div>

                <div className="checkbox-group">
                    <label className="checkbox-label">
                        <input
                            type="checkbox"
                            name="privacyPolicyAccepted"
                            checked={formData.privacyPolicyAccepted}
                            onChange={handleChange}
                            required
                            disabled={loading}
                        />
                        <span className="checkmark"></span>
                        I accept the Privacy Policy
                    </label>
                </div>

                <div className="form-buttons">
                    <button 
                        type="submit" 
                        className="btn-primary"
                        disabled={loading}
                    >
                        {loading ? 'Creating Account...' : 'Create Account'}
                    </button>
                </div>

                <div className="auth-switch">
                    <p>
                        Already have an account?{' '}
                        <button 
                            type="button" 
                            className="link-button"
                            onClick={onSwitchToLogin}
                            disabled={loading}
                        >
                            Sign in here
                        </button>
                    </p>
                </div>
            </form>
        </div>
    )
}

export default Register
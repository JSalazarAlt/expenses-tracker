import { useState } from 'react'
import { authAPI } from '../services/api'
import './Auth.css'

/**
 * Login component for user authentication.
 * 
 * Provides a form for users to sign in with their email and password.
 * Handles authentication and stores JWT token for subsequent API requests.
 * 
 * @component
 * @param {Function} onLoginSuccess - Callback function called after successful login
 * @param {Function} onSwitchToRegister - Callback to switch to registration form
 * @author Joel Salazar
 * @since 1.0
 */
function Login({ onLoginSuccess, onSwitchToRegister }) {
    const [formData, setFormData] = useState({
        email: '',
        password: ''
    })
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState('')

    /**
     * Handles input field changes and updates form state.
     */
    const handleChange = (e) => {
        const { name, value } = e.target
        setFormData(prev => ({
            ...prev,
            [name]: value
        }))
        // Clear error when user starts typing
        if (error) setError('')
    }

    /**
     * Handles form submission and user authentication.
     */
    const handleSubmit = async (e) => {
        e.preventDefault()
        setLoading(true)
        setError('')

        try {
            const response = await authAPI.login(formData)
            
            // Store JWT token and user info
            localStorage.setItem('token', response.accessToken)
            localStorage.setItem('user', JSON.stringify(response.user))
            
            // Set default authorization header for future requests
            authAPI.setAuthToken(response.accessToken)
            
            onLoginSuccess(response.user)
        } catch (error) {
            console.error('Login error:', error)
            setError(error.response?.data || 'Login failed. Please try again.')
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className="auth-container">
            <form className="auth-form" onSubmit={handleSubmit}>
                <div className="auth-header">
                    <h2>Welcome Back</h2>
                    <p>Sign in to your account to continue</p>
                </div>

                {error && (
                    <div className="error-message">
                        {error}
                    </div>
                )}

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

                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                        placeholder="Enter your password"
                        disabled={loading}
                    />
                </div>

                <div className="form-buttons">
                    <button 
                        type="submit" 
                        className="btn-primary"
                        disabled={loading}
                    >
                        {loading ? 'Signing In...' : 'Sign In'}
                    </button>
                </div>

                <div className="auth-switch">
                    <p>
                        Don't have an account?{' '}
                        <button 
                            type="button" 
                            className="link-button"
                            onClick={onSwitchToRegister}
                            disabled={loading}
                        >
                            Sign up here
                        </button>
                    </p>
                </div>
            </form>
        </div>
    )
}

export default Login
package com.suyos.tracker.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a user in the expense tracking system.
 * 
 * This class maps to the 'users' table in the database and contains
 * all the necessary fields to track individual user information.
 * 
 * @author Joel Salazar
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    

    /**
     * Unique identifier for the user record.
     * 
     * Auto-generated using database identity strategy to ensure
     * unique primary key values for each user account.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    /**
     * User's email address used for login and communication.
     * 
     * Must be unique across all users and serves as the primary
     * identifier for authentication. Used for password reset
     * and account verification emails.
     */
    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    /**
     * User's chosen username for display purposes.
     * 
     * Must be unique across all users. Optional alternative
     * identifier that can be used for login if implemented.
     */
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    /**
     * Encrypted password hash for user authentication.
     * 
     * Never stores plain text passwords. Uses BCrypt hashing
     * algorithm for secure password storage and verification.
     */
    @Column(name = "user_password", nullable = false)
    private String passwordHash;

    /**
     * Timestamp when the user's password was last changed.
     * 
     * Used for password aging policies and security auditing.
     * Helps track when users last updated their credentials.
     */
    @Column(name = "user_password_changed_at")
    private LocalDateTime passwordChangedAt;

    /**
     * Flag indicating if user must change password on next login.
     * 
     * Used for forced password resets due to security policies
     * or administrative requirements. Defaults to false.
     */
    @Builder.Default
    @Column(name = "user_must_change_password")
    private Boolean mustChangePassword = false;

    /**
     * User's first name for personal identification.
     * 
     * Required field used for personalization and display purposes
     * throughout the application interface.
     */
    @Column(name = "user_first_name", nullable = false)
    private String firstName;

    /**
     * User's last name for personal identification.
     * 
     * Required field used for personalization and display purposes
     * throughout the application interface.
     */
    @Column(name = "user_last_name", nullable = false)
    private String lastName;

    /**
     * User's phone number for contact purposes.
     * 
     * Optional field that can be used for two-factor authentication
     * or emergency contact information.
     */
    @Column(name = "user_phone")
    private String phone;

    /**
     * URL to the user's profile picture.
     * 
     * Optional field storing the location of the user's avatar image
     * for display in the application interface.
     */
    @Column(name = "user_profile_picture_url")
    private String profilePictureUrl;

    /**
     * Flag indicating if the user account is enabled.
     * 
     * Disabled accounts cannot log in or access the system.
     * Used for administrative account suspension.
     */
    @Column(name = "user_account_enabled")
    private Boolean accountEnabled;

    /**
     * Flag indicating if the user's email address has been verified.
     * 
     * Unverified users may have limited access until they confirm
     * their email address through the verification process.
     */
    @Builder.Default
    @Column(name = "user_email_verified", nullable = false)
    private Boolean emailVerified = false;

    /**
     * Flag indicating if the user account is temporarily locked.
     * 
     * Locked accounts cannot log in until the lock is removed
     * or expires. Used for security breach prevention.
     */
    @Builder.Default
    @Column(name = "user_account_locked", nullable = false)
    private Boolean accountLocked = false;

    /**
     * Counter for consecutive failed login attempts.
     * 
     * Used to track security violations and trigger account
     * lockout when threshold is exceeded. Resets on successful login.
     */
    @Builder.Default
    @Column(name = "user_failed_login_attempts")
    private Integer failedLoginAttempts = 0;

    /**
     * Timestamp when the account lock expires.
     * 
     * Null if account is not locked. When this time passes,
     * the account can be automatically unlocked.
     */
    @Column(name = "user_locked_until")
    private LocalDateTime lockedUntil;

    /**
     * Timestamp of the user's last successful login.
     * 
     * Used for security monitoring and user activity tracking.
     * Helps identify inactive accounts and suspicious activity.
     */
    @Column(name = "user_last_login_at")
    private LocalDateTime lastLoginAt;

    /**
     * Timestamp when the user accepted the terms of service.
     * 
     * Required for legal compliance and tracking user consent
     * to application terms and conditions.
     */
    @Column(name = "user_terms_accepted_at")
    private LocalDateTime termsAcceptedAt;

    /**
     * Timestamp when the user accepted the privacy policy.
     * 
     * Required for GDPR compliance and tracking user consent
     * to data processing and privacy terms.
     */
    @Column(name = "user_privacy_policy_accepted_at")
    private LocalDateTime privacyPolicyAcceptedAt;

    /**
     * User's preferred language locale.
     * 
     * Used for internationalization to display the application
     * interface in the user's preferred language.
     */
    @Column(name = "user_locale")
    private String locale;

    /**
     * User's timezone preference.
     * 
     * Used for displaying dates and times in the user's
     * local timezone throughout the application.
     */
    @Column(name = "user_timezone")
    private String timezone;

    /**
     * Timestamp when the user record was first created in the system.
     * 
     * Automatically set when the entity is first persisted.
     * This field is immutable after creation (updatable = false).
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the user record was last modified.
     * 
     * Automatically updated whenever the entity is saved.
     * Useful for tracking when changes were made to user data.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
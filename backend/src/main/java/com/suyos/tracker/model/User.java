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
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "user_password", nullable = false)
    private String passwordHash;

    @Column(name = "user_password_changed_at")
    private LocalDateTime passwordChangedAt;

    @Column(name = "user_must_change_password")
    private Boolean mustChangePassword = false;

    @Column(name = "user_first_name", nullable = false)
    private String firstName;

    @Column(name = "user_last_name", nullable = false)
    private String lastName;

    @Column(name = "user_phone")
    private String phone;

    @Column(name = "user_profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "user_account_enabled")
    private Boolean accountEnabled;

    @Column(name = "user_email_verified", nullable = false)
    private Boolean emailVerified = false;

    @Column(name = "user_account_locked", nullable = false)
    private Boolean accountLocked = false;

    @Column(name = "user_failed_login_attempts")
    private Integer failedLoginAttempts = 0;

    @Column(name = "user_locked_until")
    private LocalDateTime lockedUntil;

    @Column(name = "user_last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "user_terms_accepted_at")
    private LocalDateTime termsAcceptedAt;

    @Column(name = "user_privacy_policy_accepted_at")
    private LocalDateTime privacyPolicyAcceptedAt;

    @Column(name = "user_locale")
    private String locale;

    @Column(name = "user_timezone")
    private String timezone;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

package com.suyos.tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for user authentication information.
 * 
 * This DTO is used to capture and validate user credentials during the
 * login process. It contains only the essential fields required for
 * user authentication and session establishment.
 * 
 * @author Joel Salazar
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginDTO {

    /**
     * User's email address for authentication.
     * 
     * Must be a valid email format and match an existing user account
     * in the system for successful authentication.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    /**
     * User's password for account verification.
     * 
     * Will be validated against the stored password hash to
     * authenticate the user and establish a session.
     */
    @NotBlank(message = "Password is required")
    private String password;
    
}

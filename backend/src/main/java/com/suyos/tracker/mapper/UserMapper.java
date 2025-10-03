package com.suyos.tracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.suyos.tracker.dto.UserProfileDTO;
import com.suyos.tracker.dto.UserRegistrationDTO;
import com.suyos.tracker.dto.UserUpdateDTO;
import com.suyos.tracker.model.User;

/**
 * MapStruct mapper interface for converting between User entities and DTOs.
 * 
 * This interface defines the mapping contract between the internal User entity
 * and various user-related DTOs. MapStruct generates the implementation at compile time,
 * providing type-safe and efficient object mapping without reflection.
 * 
 * The Spring component model integration allows this mapper to be injected
 * as a Spring bean into other components.
 * 
 * @author Joel Salazar
 * @version 1.0
 * @since 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    /**
     * Converts a UserRegistrationDTO to a User entity for new user creation.
     * 
     * Used during user signup process. Sets default values for new accounts:
     * - accountEnabled: true (new users are enabled by default)
     * - emailVerified: false (requires email verification)
     * - Other security fields are ignored and handled by business logic
     * 
     * @param userRegistrationDTO the registration DTO to convert
     * @return the corresponding User entity ready for persistence
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true) 
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "accountEnabled", constant = "true")
    @Mapping(target = "emailVerified", constant = "false")
    @Mapping(target = "accountLocked", ignore = true)
    @Mapping(target = "failedLoginAttempts", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "locale", ignore = true)
    @Mapping(target = "lockedUntil", ignore = true)
    @Mapping(target = "mustChangePassword", ignore = true)
    @Mapping(target = "passwordChangedAt", ignore = true)
    @Mapping(target = "privacyPolicyAcceptedAt", ignore = true)
    @Mapping(target = "profilePictureUrl", ignore = true)
    @Mapping(target = "termsAcceptedAt", ignore = true)
    @Mapping(target = "timezone", ignore = true)
    User toEntity(UserRegistrationDTO userRegistrationDTO);
    
    /**
     * Converts a UserUpdateDTO to a User entity for profile updates.
     * 
     * Used when users update their profile information. Security-sensitive
     * fields like email, password, and account status are ignored to prevent
     * unauthorized modifications through profile update endpoints.
     * 
     * @param userUpdateDTO the update DTO to convert
     * @return the corresponding User entity with updated profile fields
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true) 
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "accountEnabled", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "accountLocked", ignore = true)
    @Mapping(target = "failedLoginAttempts", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "lockedUntil", ignore = true)
    @Mapping(target = "mustChangePassword", ignore = true)
    @Mapping(target = "passwordChangedAt", ignore = true)
    @Mapping(target = "privacyPolicyAcceptedAt", ignore = true)
    @Mapping(target = "termsAcceptedAt", ignore = true)
    User toEntity(UserUpdateDTO userUpdateDTO);

    /**
     * Converts a User entity to a UserProfileDTO for API responses.
     * 
     * Used when returning user profile information to clients.
     * Excludes sensitive fields like password and includes all
     * safe profile information for display purposes.
     * 
     * @param user the entity to convert
     * @return the corresponding UserProfileDTO
     */
    UserProfileDTO toProfileDTO(User user);

    /**
     * Converts a User entity to a UserUpdateDTO for form population.
     * 
     * Used when pre-populating user profile edit forms with current
     * user information. Only includes fields that users are allowed
     * to modify through the profile update interface.
     * 
     * @param user the entity to convert
     * @return the corresponding UserUpdateDTO
     */
    UserUpdateDTO toUpdateDTO(User user);
    
}

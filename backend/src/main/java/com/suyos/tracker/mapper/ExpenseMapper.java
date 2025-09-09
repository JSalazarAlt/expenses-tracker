package com.suyos.tracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.suyos.tracker.dto.ExpenseDTO;
import com.suyos.tracker.model.Expense;

/**
 * MapStruct mapper interface for converting between Expense entities and DTOs.
 * 
 * This interface defines the mapping contract between the internal Expense entity
 * and the external ExpenseDTO. MapStruct generates the implementation at compile time,
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
public interface ExpenseMapper {
    
    /**
     * Converts an ExpenseDTO to an Expense entity.
     * 
     * Used when creating or updating expenses from API requests.
     * The mapping is automatic based on matching field names.
     * Timestamp fields are ignored as they're managed by Hibernate.
     * 
     * @param expenseDTO the DTO to convert
     * @return the corresponding Expense entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Expense toEntity(ExpenseDTO expenseDTO);

    /**
     * Converts an Expense entity to an ExpenseDTO.
     * 
     * Used when returning expense data in API responses.
     * Includes all entity fields in the DTO representation.
     * 
     * @param expense the entity to convert
     * @return the corresponding ExpenseDTO
     */
    ExpenseDTO toDTO(Expense expense);

}

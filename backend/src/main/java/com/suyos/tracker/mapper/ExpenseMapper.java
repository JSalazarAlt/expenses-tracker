package com.suyos.tracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.suyos.tracker.dto.ExpenseDTO;
import com.suyos.tracker.model.Expense;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpenseMapper {
    
    Expense toEntity(ExpenseDTO expenseDTO);

    ExpenseDTO toDTO(Expense expense);

}

package com.suyos.tracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.suyos.tracker.model.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDTO {
    
    @JsonProperty("expenseId")
    private Long expenseId;

    @JsonProperty("expenseDescription")
    private String expenseDescription;

    @JsonProperty("expenseAmount")
    private BigDecimal expenseAmount;

    @JsonProperty("expenseDate")
    private LocalDate expenseDate;

    @JsonProperty("expenseCategory")
    private Category expenseCategory;

}

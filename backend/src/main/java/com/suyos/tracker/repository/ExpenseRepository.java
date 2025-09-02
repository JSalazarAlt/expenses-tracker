package com.suyos.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suyos.tracker.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
}

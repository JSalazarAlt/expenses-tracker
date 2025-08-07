package com.projects.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.tracker.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
}

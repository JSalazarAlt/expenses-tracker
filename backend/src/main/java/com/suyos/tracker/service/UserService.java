package com.suyos.tracker.service;

import org.springframework.stereotype.Service;

import com.suyos.tracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    /** Repository for expense data access operations */
    private final UserRepository userRepository;

}

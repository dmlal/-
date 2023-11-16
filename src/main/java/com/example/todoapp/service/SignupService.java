package com.example.todoapp.service;

import com.example.todoapp.repository.SignupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final SignupRepository signupRepository;
}

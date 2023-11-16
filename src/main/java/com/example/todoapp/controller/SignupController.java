package com.example.todoapp.controller;

import com.example.todoapp.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;



}

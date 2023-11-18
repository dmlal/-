package com.example.todoapp.service;

import com.example.todoapp.controller.exception.DuplicatedUsernameException;
import com.example.todoapp.dto.SignupRequestDto;
import com.example.todoapp.entity.User;
import com.example.todoapp.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;

    @Transactional
    public void signUp(@Valid SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw new DuplicatedUsernameException();
        }
        User user = new User(username, requestDto.getPassword());
        userRepository.save(user);
    }
}

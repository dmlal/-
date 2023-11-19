package com.example.todoapp.controller;

import com.example.todoapp.controller.exception.DuplicatedUsernameException;
import com.example.todoapp.controller.exception.TodoNotFoundException;
import com.example.todoapp.controller.exception.UserNotFoundException;
import com.example.todoapp.dto.SignupRequestDto;
import com.example.todoapp.dto.exception.ErrorResponseDto;
import com.example.todoapp.service.SignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @GetMapping("/signup")
    public String showSignup() {
        return "signup";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignupRequestDto requestDto) {
        signupService.signUp(requestDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
//    public

    @ExceptionHandler(DuplicatedUsernameException.class)
    public ResponseEntity<ErrorResponseDto> duplicatedUsernameExceptionHandler(DuplicatedUsernameException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        HttpStatus.BAD_REQUEST.value(), e.getMessage()
                ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> userNotFoundExceptionHandler(UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        HttpStatus.BAD_REQUEST.value(), e.getMessage()
                ));
    }

}

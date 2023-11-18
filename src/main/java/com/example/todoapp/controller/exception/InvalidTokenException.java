package com.example.todoapp.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(){
        super("토큰이 유효하지 않습니다.");
    }
}

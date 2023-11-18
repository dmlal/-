package com.example.todoapp.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotYoursException extends RuntimeException{
    public NotYoursException(){
        super("작성자만 삭제/수정할 수 있습니다.");
    }
}

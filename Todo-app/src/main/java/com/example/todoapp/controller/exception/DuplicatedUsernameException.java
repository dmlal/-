package com.example.todoapp.controller.exception;

public class DuplicatedUsernameException extends RuntimeException{
    public DuplicatedUsernameException(){
        super("중복된 username 입니다.");
    }
}

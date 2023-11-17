package com.example.todoapp.dto;

import lombok.Getter;

@Getter
public class TodoUpdateRequestDto {
    private String username;
    private String password;
    private String title;
    private String content;
}

package com.example.todoapp.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {

    // 토큰 받아올 필드 필요
    private Long id;
    private String username;
    private String password;
    private String title;
    private String content;
}

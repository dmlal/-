package com.example.todoapp.dto;

import lombok.Getter;

@Getter
public class ReplyRequestDto {
    private Long todoId;
    private String username;
    private String content;
}

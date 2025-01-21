package com.sparta.anonymousboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {
    private Long id;
    private String userName;
    private String title;
    private String content;
    private String password;
}

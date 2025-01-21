package com.sparta.anonymousboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateRequestDto {
    private Long id;

    private String userName;
    private String title;
    private String content;
    private String password;

    public UpdateRequestDto(Long id, String userName, String title, String content, String password) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.password = password;
    }
}

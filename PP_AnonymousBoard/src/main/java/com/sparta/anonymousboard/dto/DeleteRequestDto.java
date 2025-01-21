package com.sparta.anonymousboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteRequestDto {
    private Long id;
    private String password;

    public DeleteRequestDto(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public DeleteRequestDto(BoardRequestDto requestDto) {
        this.id = requestDto.getId();
        this.password = requestDto.getPassword();
    }
}

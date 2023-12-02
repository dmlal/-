package com.sparta.newsfeedt6.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EmailRequestDto {
    @NotNull
    private String email;
}

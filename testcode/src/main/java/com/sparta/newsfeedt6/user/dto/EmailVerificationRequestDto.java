package com.sparta.newsfeedt6.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVerificationRequestDto {
    @NotNull
    private String email;

    @NotNull
    private String authCode;
}

package com.sparta.newsfeedt6.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9-_]+@(naver\\.com|gmail\\.com)$", message = "naver.com 또는 gmail.com 이메일이 아닙니다.")
    private String email;
    private String introduction;

}

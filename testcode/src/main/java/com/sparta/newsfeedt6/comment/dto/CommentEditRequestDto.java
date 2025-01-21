package com.sparta.newsfeedt6.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentEditRequestDto {
    @NotBlank
    private String content;
}

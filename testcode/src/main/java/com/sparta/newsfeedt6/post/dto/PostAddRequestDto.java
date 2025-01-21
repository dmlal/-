package com.sparta.newsfeedt6.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 게시글 등록 RequestDto
@Getter
@RequiredArgsConstructor
public class PostAddRequestDto {
    private final String title;
    private final String content;
}
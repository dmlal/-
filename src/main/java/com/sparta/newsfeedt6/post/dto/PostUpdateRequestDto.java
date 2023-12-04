package com.sparta.newsfeedt6.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 게시글 수정 RequestDto
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequestDto {
    private String title;
    private String content;
}

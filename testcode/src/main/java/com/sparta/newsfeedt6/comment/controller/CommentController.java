package com.sparta.newsfeedt6.comment.controller;

import com.sparta.newsfeedt6.comment.dto.CommentEditRequestDto;
import com.sparta.newsfeedt6.comment.dto.CommentRequestDto;
import com.sparta.newsfeedt6.comment.dto.CommentResponseDto;
import com.sparta.newsfeedt6.comment.service.CommentService;
import com.sparta.newsfeedt6.security.UserDetailsImpl;
import com.sparta.newsfeedt6.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
@Transactional
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(postId, requestDto, userDetails.getUser());

        return ResponseEntity.ok(responseDto);
    }
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentEditRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok("success");
    }

}

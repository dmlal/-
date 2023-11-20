package com.example.todoapp.controller;

import com.example.todoapp.controller.exception.InvalidTokenException;
import com.example.todoapp.dto.ReplyRequestDto;
import com.example.todoapp.dto.ReplyResponseDto;
import com.example.todoapp.entity.Reply;
import com.example.todoapp.jwt.JwtUtil;
import com.example.todoapp.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final JwtUtil jwtUtil;
    private final ReplyService replyService;


    @PostMapping("/reply-posts")
    public ResponseEntity<Reply> replyPost(@PathVariable Long todoId, @RequestBody ReplyRequestDto requestDto, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer", "");
        if (!jwtUtil.validateToken(token)) {
            throw new InvalidTokenException();
        }

        Reply reply = replyService.replyPost(requestDto);
        return new ResponseEntity<>(reply, HttpStatus.CREATED);

    }

}

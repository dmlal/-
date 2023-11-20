package com.example.todoapp.controller;

import com.example.todoapp.controller.exception.InvalidTokenException;
import com.example.todoapp.controller.exception.NotYoursException;
import com.example.todoapp.dto.ReplyRequestDto;
import com.example.todoapp.dto.ReplyResponseDto;
import com.example.todoapp.entity.Reply;
import com.example.todoapp.jwt.JwtUtil;
import com.example.todoapp.service.ReplyService;
import com.example.todoapp.service.TodoService;
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
    private final TodoService todoService;


    @PostMapping("/reply-posts")
    public ResponseEntity<Reply> replyPost(@PathVariable Long todoId, @RequestBody ReplyRequestDto requestDto, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer", "");
        if (!jwtUtil.validateToken(token)) {
            throw new InvalidTokenException();
        }

        Reply reply = replyService.replyPost(requestDto);
        return new ResponseEntity<>(reply, HttpStatus.CREATED);
    }

    @PatchMapping("/reply-edit")
    public Reply replyEdit(@PathVariable Long replyId, @RequestBody ReplyRequestDto replyRequestDto, @RequestHeader("Authorization") String token) {
        verifyToken(replyId, token);
        return replyService.replyEdit(replyId, replyRequestDto, token);
    }

    @DeleteMapping("/reply-delete")
    public ResponseEntity<?> replyDelete (@PathVariable Long replyId,@RequestHeader("Authorization") String token ) {
        verifyToken(replyId, token);
        replyService.replyDelete(replyId);

        return new ResponseEntity<>("성공적으로 삭제되었습니다.", HttpStatus.OK);
    }

    private void verifyToken(Long todoId, String token) {
        token = token.replace("Bearer", "");
        if (!jwtUtil.validateToken(token)) {
            throw new InvalidTokenException();
        }

        String usernameInToken = jwtUtil.getUserInfoFromTokenByString(token);
        String username = todoService.getUsername(todoId);

        if (!usernameInToken.equals(username)) {
            throw new NotYoursException();
        }
    }

}




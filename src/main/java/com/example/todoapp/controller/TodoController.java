package com.example.todoapp.controller;

import com.example.todoapp.controller.exception.AuthException;
import com.example.todoapp.controller.exception.InvalidTokenException;
import com.example.todoapp.controller.exception.NotYoursException;
import com.example.todoapp.controller.exception.TodoNotFoundException;
import com.example.todoapp.dto.TodoRequestDto;
import com.example.todoapp.dto.TodoResponseDto;
import com.example.todoapp.dto.TodoUpdateRequestDto;
import com.example.todoapp.dto.exception.ErrorResponseDto;
import com.example.todoapp.jwt.JwtUtil;
import com.example.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final JwtUtil jwtUtil;


    // 작성
    @PostMapping("/posts")
    public ResponseEntity<TodoResponseDto> todoPost(@RequestBody TodoRequestDto requestDto, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer", "");
        if (!jwtUtil.validateToken(token)) {
            throw new InvalidTokenException();
        }
        TodoResponseDto responseDto = todoService.todoPost(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 조회
    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto> getTodo(@PathVariable Long todoId) {
        TodoResponseDto responseDto = todoService.getTodo(todoId);
        return ResponseEntity.ok(responseDto);
    }

    // 전체목록 조회
    @GetMapping("/posts")
    public ResponseEntity<List<TodoResponseDto>> getTodos() {
        List<TodoResponseDto> responseDto = todoService.getTodos();
        return ResponseEntity.ok(responseDto);
    }

    // 수정
    @PatchMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long todoId, @RequestBody TodoUpdateRequestDto requestDto,
                                                      @RequestHeader("Authorization") String token) {
        verifyToken(todoId, token);

        TodoResponseDto responseDto = todoService.updateTodo(todoId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 삭제
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId, @RequestHeader("Authorization") String token) {
        verifyToken(todoId, token);

        todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }
    
    // 완료 버튼
    @PatchMapping("/{todoId}/complete")
    public ResponseEntity<TodoResponseDto> completeTodo (@PathVariable Long todoId, @RequestHeader("Authorization") String token) {
        verifyToken(todoId, token);
        TodoResponseDto responseDto = todoService.completeTodo(todoId);
        return ResponseEntity.ok(responseDto);
    }

    // 토큰 검증 메소드
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

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> todoNotFoundExceptionHandler(TodoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(
                        HttpStatus.NOT_FOUND.value(), e.getMessage()
                ));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponseDto> authExceptionHandler(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDto(
                        HttpStatus.UNAUTHORIZED.value(), e.getMessage()
                ));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponseDto> invalidTokenExceptionHandler(InvalidTokenException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        HttpStatus.BAD_REQUEST.value(), e.getMessage()
                ));
    }

    @ExceptionHandler(NotYoursException.class)
    public ResponseEntity<ErrorResponseDto> notYoursExceptionHandler(NotYoursException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        HttpStatus.BAD_REQUEST.value(), e.getMessage()
                ));
    }
}

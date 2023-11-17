package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoRequestDto;
import com.example.todoapp.dto.TodoResponseDto;
import com.example.todoapp.dto.TodoUpdateRequestDto;
import com.example.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;


    // 작성
    @PostMapping("/posts")
    public ResponseEntity<TodoResponseDto> todoPost(@RequestBody TodoRequestDto requestDto) {
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
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long todoId, @RequestBody TodoUpdateRequestDto requestDto) {
        TodoResponseDto responseDto = todoService.updateTodo(todoId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 삭제
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId, @RequestHeader("password") String password){
        todoService.deleteTodo(todoId, password);
        return ResponseEntity.noContent().build();
    }


}

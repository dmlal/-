package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoRequestDto;
import com.example.todoapp.dto.TodoResponseDto;
import com.example.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;


    // 작성
    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public TodoResponseDto todoPost(@RequestBody TodoRequestDto requestDto) {
        TodoResponseDto responseDto = todoService.todoPost(requestDto);
        return responseDto;
    }

    // 조회
    @GetMapping("/{todoId}")
    public TodoResponseDto getTodo(@PathVariable Long todoId){
        return todoService.getTodo(todoId);
    }

    // 전체목록 조회

    // 수정

    // 삭제

    // 완료처리 ?  메소드가 굳이 필요한가 의문이긴함

}

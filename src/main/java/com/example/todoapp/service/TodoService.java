package com.example.todoapp.service;

import com.example.todoapp.dto.TodoRequestDto;
import com.example.todoapp.dto.TodoResponseDto;
import com.example.todoapp.dto.TodoUpdateRequestDto;
import com.example.todoapp.entity.Todo;
import com.example.todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    public TodoResponseDto todoPost(TodoRequestDto requestDto) {
        Todo todo = new Todo(requestDto);
        Todo saveTodo = todoRepository.save(todo);
        TodoResponseDto responseDto = new TodoResponseDto(saveTodo);
        return responseDto;
    }

    public TodoResponseDto getTodo(Long todoId) {
        Todo todo = getTodoENtity(todoId);

        return new TodoResponseDto(todo);
    }


    public List<TodoResponseDto> getTodos() {
        return todoRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(TodoResponseDto::new).toList();
    }

    @Transactional
    public TodoResponseDto updateTodo(Long todoId, TodoUpdateRequestDto requestDto) {
        Todo todo = getTodoENtity(todoId);
        if (!todo.getPassword().equals(requestDto.getPassword())) {
            throw new NullPointerException("비밀번호가 일치하지 않습니다.");
        }
        todo.update(requestDto);
        return new TodoResponseDto((todo));
    }


    private Todo getTodoENtity(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(() -> new NullPointerException("해당 Todo카드를 찾을 수 없습니다."));
    }
}

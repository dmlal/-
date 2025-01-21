package com.example.todoapp.service;

import com.example.todoapp.controller.exception.AuthException;
import com.example.todoapp.controller.exception.TodoNotFoundException;
import com.example.todoapp.dto.TodoRequestDto;
import com.example.todoapp.dto.TodoResponseDto;
import com.example.todoapp.dto.TodoUpdateRequestDto;
import com.example.todoapp.entity.Todo;
import com.example.todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Todo todo = getTodoEntity(todoId);

        return new TodoResponseDto(todo);
    }


    public List<TodoResponseDto> getTodos() {
        return todoRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(TodoResponseDto::new).toList();
    }

    @Transactional
    public TodoResponseDto updateTodo(Long todoId, TodoUpdateRequestDto requestDto) {
        Todo todo = getTodoEntity(todoId);
        todo.update(requestDto);
        return new TodoResponseDto((todo));
    }

    @Transactional
    public void deleteTodo(Long todoId) {
        Todo todo = getTodoEntity(todoId);
        todoRepository.delete(todo);
    }

    private Todo getTodoEntity(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException("해당 Todo카드를 찾을 수 없습니다."));
    }


    
    // 토큰의 아이디와 비교하기 위한 메소드 작성
    public String getUsername(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("ID가 존재하지 않습니다.")
        );
        return todo.getUsername();
    }

    public TodoResponseDto completeTodo(Long todoId) {
        Todo todo = getTodoEntity(todoId);
        todo.completeTodo();
        todoRepository.save(todo);
        return new TodoResponseDto(todo);
    }
}

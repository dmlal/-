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
        verifyPassword(todo, requestDto.getPassword());
        todo.update(requestDto);
        return new TodoResponseDto((todo));
    }

    @Transactional
    public void deleteTodo(Long todoId, String password) {
        Todo todo = getTodoEntity(todoId);
        verifyPassword(todo, password);
        todoRepository.delete(todo);
    }

    private Todo getTodoEntity(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException("해당 Todo카드를 찾을 수 없습니다."));
    }

    private static void verifyPassword(Todo todo, String password) {
        if (!todo.passwordMatches(password)) {
            throw new AuthException("비밀번호가 일치하지 않습니다.");
        }
    }
}

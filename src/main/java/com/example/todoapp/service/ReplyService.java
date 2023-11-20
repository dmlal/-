package com.example.todoapp.service;

import com.example.todoapp.controller.exception.TodoNotFoundException;
import com.example.todoapp.dto.ReplyRequestDto;
import com.example.todoapp.dto.ReplyResponseDto;
import com.example.todoapp.entity.Reply;
import com.example.todoapp.entity.Todo;
import com.example.todoapp.repository.ReplyRepository;
import com.example.todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final TodoRepository todoRepository;
    public Reply replyPost(ReplyRequestDto replyRequestDto) {
        Todo todo = todoRepository.findById(replyRequestDto.getTodoId()).orElseThrow(
                () -> new TodoNotFoundException("해당 Todo카드를 찾을 수 없습니다."));

        Reply reply = new Reply();
        reply.setContent(replyRequestDto.getContent());
        reply.setTodo(todo);

        return replyRepository.save(reply);
    }
}

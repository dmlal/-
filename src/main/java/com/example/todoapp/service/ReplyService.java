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

    public Reply replyEdit(Long replyId, ReplyRequestDto replyRequestDto, String token) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new TodoNotFoundException("해당 댓글을 찾을 수 없습니다.")
        );
        reply.update(replyRequestDto);
        return replyRepository.save(reply);
    }


    public void replyDelete(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new TodoNotFoundException("해당 댓글을 찾을 수 없습니다."));

        replyRepository.delete(reply);
    }
}

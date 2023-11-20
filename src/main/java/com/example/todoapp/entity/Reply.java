package com.example.todoapp.entity;

import com.example.todoapp.dto.ReplyRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reply")
@NoArgsConstructor
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="todo_id")
    @JsonBackReference
    private Todo todo;

    private String content;

    public void update(ReplyRequestDto replyRequestDto) {
        this.content = replyRequestDto.getContent();
    }
}

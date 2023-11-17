package com.example.todoapp.entity;

import com.example.todoapp.dto.TodoRequestDto;
import com.example.todoapp.dto.TodoUpdateRequestDto;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "todo")
@NoArgsConstructor
public class Todo extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(nullable = false)
    private String title;
    @Column
    private String content;

    public Todo(TodoRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public  void update(TodoUpdateRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public boolean passwordMatches(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}

package com.example.todoapp.repository;

import com.example.todoapp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUsernameOrderByCreatedAtDesc(String username);
    List<Todo> findAllByOrderByCreatedAtDesc();

}

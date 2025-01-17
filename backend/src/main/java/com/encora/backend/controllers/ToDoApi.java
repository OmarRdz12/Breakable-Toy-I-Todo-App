package com.encora.backend.controllers;

import com.encora.backend.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ToDoApi {
    @GetMapping("/todos")
    ResponseEntity<List<Task>> getToDos();

    @PostMapping("/todos")
    ResponseEntity<Task> createTodo(@RequestBody(required = true) Task task);
}

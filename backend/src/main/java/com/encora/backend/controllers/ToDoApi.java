package com.encora.backend.controllers;

import com.encora.backend.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ToDoApi {
    @GetMapping("/todos")
    ResponseEntity<List<Task>> getToDos(@RequestParam int offset, int limit, String priority, String state, String text); //limit: number of records --- offset: first id to return

    @PostMapping("/todos")
    ResponseEntity<Task> createTodo(@RequestBody(required = true) Task task);

    @PutMapping("/todos/{id}")
    ResponseEntity<Task> updateTodo(@RequestBody(required = true) Task task, @PathVariable(value = "id") Long toDoId);

    @PutMapping("/todos/{id}/undone")
    ResponseEntity<Task> undoneTodo(@PathVariable(value = "id") Long toDoId);

    @PutMapping("/todos/{id}/done")
    ResponseEntity<Task> doneTodo(@PathVariable(value = "id") Long toDoId);

}

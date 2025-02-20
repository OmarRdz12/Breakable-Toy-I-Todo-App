package com.encora.backend.controllers;

import com.encora.backend.model.CustomResponse;
import com.encora.backend.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ToDoApi {
    @GetMapping("/todos")
    ResponseEntity<CustomResponse<Task>> getToDos(@RequestParam int page, int limit, String priority, String state, String name, String dueDateSort, String prioritySort); //limit: number of records --- page: current page

    @PostMapping("/todos")
    ResponseEntity<Task> createTodo(@RequestBody(required = true) Task task);

    @PutMapping("/todos/{id}")
    ResponseEntity<Task> updateTodo(@RequestBody(required = true) Task task, @PathVariable(value = "id") Long toDoId);

    @PutMapping("/todos/{id}/undone")
    ResponseEntity<Task> undoneTodo(@PathVariable(value = "id") Long toDoId);

    @PutMapping("/todos/{id}/done")
    ResponseEntity<Task> doneTodo(@PathVariable(value = "id") Long toDoId);

    @DeleteMapping("/todos/{id}")
    ResponseEntity<Task> deleteTodo(@PathVariable(value = "id") Long toDoId);
}

package com.encora.backend.controllers;

import com.encora.backend.model.Task;
import com.encora.backend.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ToDoApiController implements ToDoApi {
    ToDoService toDoService;

    public ToDoApiController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @Override
    public ResponseEntity<List<Task>> getToDos(@RequestParam int offset, @RequestParam int limit) {
        return new ResponseEntity<>(toDoService.getAllToDos(offset, limit), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Task> createTodo(@RequestBody(required = true) Task task) {
        return new ResponseEntity<>(toDoService.save(task), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Task> updateTodo(@RequestBody Task task, @PathVariable(value = "id") Long toDoId) {
        return new ResponseEntity<>(toDoService.updateTask(task, toDoId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Task> undoneTodo(Long toDoId) {
        return new ResponseEntity<>(toDoService.undoneTask(toDoId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Task> doneTodo(Long toDoId) {
        return new ResponseEntity<>(toDoService.doneTask(toDoId), HttpStatus.OK);
    }
}

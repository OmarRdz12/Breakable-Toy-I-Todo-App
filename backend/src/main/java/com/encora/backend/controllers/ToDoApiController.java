package com.encora.backend.controllers;

import com.encora.backend.model.Task;
import com.encora.backend.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ToDoApiController implements ToDoApi {
    ToDoService toDoService;

    @Autowired
    public ToDoApiController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @Override
    public ResponseEntity<List<Task>> getToDos() {
        return new ResponseEntity<>(toDoService.getAllToDos(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Task> createTodo(@RequestBody(required = true) Task task) {
        return new ResponseEntity<>(toDoService.save(task), HttpStatus.CREATED);
    }
}

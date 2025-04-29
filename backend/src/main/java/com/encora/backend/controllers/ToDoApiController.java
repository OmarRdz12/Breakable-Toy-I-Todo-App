package com.encora.backend.controllers;

import com.encora.backend.exception.TaskException;
import com.encora.backend.model.CustomResponse;
import com.encora.backend.model.Task;
import com.encora.backend.service.ToDoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<CustomResponse<Task>> getToDos(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam (name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam (name = "priority", required = false, defaultValue = "all") String priority,
            @RequestParam (name = "state", required = false, defaultValue = "all") String state,
            @RequestParam (name = "name", required = false, defaultValue = "") String name,
            @RequestParam (name = "dueDateSort", required = false, defaultValue = "") String dueDateSort,
            @RequestParam (name = "prioritySort", required = false, defaultValue = "") String prioritySort
    ) {
        if (page < 1 || limit < 1) {
            throw new TaskException("Parameters must be greater than or equal to 1");
        }
        List<String> validPriorities = List.of("all", "low", "medium", "high");
        if (!validPriorities.contains(priority.toLowerCase())) {
            throw new TaskException("Invalid priority " + priority);
        }
        List<String> validStates = List.of("all", "true", "false");
        if (!validStates.contains(state.toLowerCase())) {
            throw new TaskException("Invalid state: " + state);
        }

        return new ResponseEntity<>(toDoService.getAllToDos(page, limit, priority, state, name, dueDateSort, prioritySort), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Task> createTodo(@Valid @RequestBody(required = true) Task task) {
        return new ResponseEntity<>(toDoService.save(task), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Task> updateTodo(@RequestBody Task task, @PathVariable(value = "id") Long toDoId) {
        if (toDoId == null)
            throw new TaskException("ID cannot be null");
        return new ResponseEntity<>(toDoService.updateTask(task, toDoId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Task> undoneTodo(Long toDoId) {
        if (toDoId == null)
            throw new TaskException("ID cannot be null");
        return new ResponseEntity<>(toDoService.undoneTask(toDoId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Task> doneTodo(Long toDoId) {
        if (toDoId == null)
            throw new TaskException("ID cannot be null");
        return new ResponseEntity<>(toDoService.doneTask(toDoId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Task> deleteTodo(Long toDoId) {
        if (toDoId == null)
            throw new TaskException("ID cannot be null");
        return new ResponseEntity<>(toDoService.deleteTask(toDoId), HttpStatus.OK);
    }
}

package com.encora.backend.service;

import com.encora.backend.model.Task;

import java.util.List;

public interface ToDoService {
    List<Task> getAllToDos();
    Task save(Task task);
}

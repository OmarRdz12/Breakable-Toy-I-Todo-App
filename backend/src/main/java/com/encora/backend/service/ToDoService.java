package com.encora.backend.service;

import com.encora.backend.model.Task;

import java.util.List;

public interface ToDoService {
    List<Task> getAllToDos(int offset, int limit, String priority, String state, String name);
    Task save(Task task);
    Task updateTask(Task task, Long id);
    Task undoneTask(Long id);
    Task doneTask(Long id);
}

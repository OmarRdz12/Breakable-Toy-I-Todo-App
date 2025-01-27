package com.encora.backend.service;

import com.encora.backend.model.CustomResponse;
import com.encora.backend.model.Task;

public interface ToDoService {
    CustomResponse<Task> getAllToDos(int page, int limit, String priority, String state, String name);
    Task save(Task task);
    Task updateTask(Task task, Long id);
    Task undoneTask(Long id);
    Task doneTask(Long id);
    Task deleteTask(Long id);
}

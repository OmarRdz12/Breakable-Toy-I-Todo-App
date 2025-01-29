package com.encora.backend.dao;

import com.encora.backend.model.CustomResponse;
import com.encora.backend.model.Stat;
import com.encora.backend.model.Task;

public interface ToDoDao {
    Task save(Task task);
    CustomResponse<Task> findAll(int page, int limit, String priority, String state, String name, String dueDateSort, String prioritySort);
    Task updateTask(Task task, Long id);
    Task undoneTask(Long id);
    Task doneTask(Long id);
    Task deleteTask(Long id);
    Stat getStats();
}

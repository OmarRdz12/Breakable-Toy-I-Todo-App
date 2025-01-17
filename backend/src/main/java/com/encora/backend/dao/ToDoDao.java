package com.encora.backend.dao;

import com.encora.backend.model.Task;

import java.util.List;

public interface ToDoDao {
    Task save(Task task);
    List<Task> findAll(int offset, int limit);
    Task updateTask(Task task, Long id);
    Task undoneTask(Long id);
    Task doneTask(Long id);
}

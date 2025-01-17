package com.encora.backend.dao;

import com.encora.backend.model.Task;

import java.util.List;

public interface ToDoDao {
    Task save(Task task);
    List<Task> findAll();
}

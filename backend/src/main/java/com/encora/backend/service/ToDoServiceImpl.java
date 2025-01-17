package com.encora.backend.service;

import com.encora.backend.dao.ToDoDao;
import com.encora.backend.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService{
    ToDoDao toDoDao;

    @Autowired
    public ToDoServiceImpl(ToDoDao toDoDao) {
        this.toDoDao = toDoDao;
    }

    @Override
    public List<Task> getAllToDos() {
        return toDoDao.findAll();
    }

    @Override
    public Task save(Task task) {
        return toDoDao.save(task);
    }
}

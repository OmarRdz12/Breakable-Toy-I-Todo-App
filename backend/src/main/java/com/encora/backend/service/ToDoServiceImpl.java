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
    public List<Task> getAllToDos(int offset, int limit, String priority, String state, String text) {
        return toDoDao.findAll(offset, limit, priority, state, text);
    }

    @Override
    public Task save(Task task) {
        return toDoDao.save(task);
    }

    @Override
    public Task updateTask(Task task, Long id) {
        return toDoDao.updateTask(task, id);
    }

    @Override
    public Task undoneTask(Long id) {
        return toDoDao.undoneTask(id);
    }

    @Override
    public Task doneTask(Long id) {
        return toDoDao.doneTask(id);
    }
}

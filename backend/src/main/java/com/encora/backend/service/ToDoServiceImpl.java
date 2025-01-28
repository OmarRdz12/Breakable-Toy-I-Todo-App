package com.encora.backend.service;

import com.encora.backend.dao.ToDoDao;
import com.encora.backend.model.CustomResponse;
import com.encora.backend.model.Task;
import org.springframework.stereotype.Service;

@Service
public class ToDoServiceImpl implements ToDoService{
    ToDoDao toDoDao;

    public ToDoServiceImpl(ToDoDao toDoDao) {
        this.toDoDao = toDoDao;
    }

    @Override
    public CustomResponse<Task> getAllToDos(int page, int limit, String priority, String state, String name, String dueDateSort, String prioritySort) {
        return toDoDao.findAll(page, limit, priority, state, name, dueDateSort, prioritySort);
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

    @Override
    public Task deleteTask(Long id) {
        return toDoDao.deleteTask(id);
    }
}

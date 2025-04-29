package com.encora.backend.service;

import com.encora.backend.dao.ToDoDao;
import com.encora.backend.exception.TaskException;
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
        CustomResponse<Task> response = toDoDao.findAll(page, limit, priority, state, name, dueDateSort, prioritySort);
        if (response.getData().isEmpty() || response.getData() == null)
            throw new TaskException("There is no task to show");
        return response;
    }

    @Override
    public Task save(Task task) {
        if (task == null) {
            throw new TaskException("Task cannot be null");
        }
        Task savedTask = toDoDao.save(task);
        if (savedTask == null || savedTask.getId() == null)
            throw new TaskException("Error saving task");
        return savedTask;
    }

    @Override
    public Task updateTask(Task task, Long id) {
        Task updatedTask = toDoDao.updateTask(task, id);
        if (updatedTask == null)
            throw new TaskException("Task with ID: " + id + " was not found");
        return updatedTask;
    }

    @Override
    public Task undoneTask(Long id) {
        if (id == null)
            throw new TaskException("ID cannot be null");
        Task undoneTask = toDoDao.undoneTask(id);
        if (undoneTask == null)
            throw new TaskException("Task with ID: " + id + " was not found");
        return undoneTask;
    }

    @Override
    public Task doneTask(Long id) {
        Task doneTask = toDoDao.doneTask(id);
        if (doneTask == null)
            throw new TaskException("Task with ID: " + id + " was not found");
        return doneTask;
    }

    @Override
    public Task deleteTask(Long id) {
        Task deletedTask = toDoDao.deleteTask(id);
        if (deletedTask == null) {
            throw new TaskException("Task with ID: " + id + " was not found");
        }
        return deletedTask;
    }

}

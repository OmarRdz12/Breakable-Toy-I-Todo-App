package com.encora.backend.dao;

import com.encora.backend.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ToDoDaoImpl implements ToDoDao{
    private static final List<Task> toDos = new ArrayList<>();

    static {
        toDos.add(new Task(1L, null, false, null, null, "high", "Create the repository"));
        toDos.add(new Task(2L, null, false, null, null, "high", "Create the spring project"));
        toDos.add(new Task(3L, null, false, null, null, "medium", "Make the endpoints"));
        toDos.add(new Task(4L, null, false, null, null, "low", "Test the endpoints"));
    }

    @Override
    public Task save(Task task) {
        Long id = toDos.stream().mapToLong(Task::getId).max().orElse(0) + 1;
        task.setId(id);
        toDos.add(task);
        return task;
    }

    @Override
    public List<Task> findAll() {
        return toDos;
    }
}

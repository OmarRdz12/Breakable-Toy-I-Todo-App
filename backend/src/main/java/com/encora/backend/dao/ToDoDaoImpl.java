package com.encora.backend.dao;

import com.encora.backend.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ToDoDaoImpl implements ToDoDao{
    private static final List<Task> toDos = new ArrayList<>();

    static {
        toDos.add(new Task(1L, null, false, null, null, "high", "Create the repository"));
        toDos.add(new Task(2L, null, false, null, null, "high", "Create the spring project"));
        toDos.add(new Task(3L, null, false, null, null, "medium", "Make the endpoints"));
        toDos.add(new Task(4L, null, false, null, null, "low", "Test the endpoints"));
        toDos.add(new Task(5L, new Date(), true, null, null, "low", "Push commits"));
    }

    @Override
    public Task save(Task task) {
        Long id = toDos.stream().mapToLong(Task::getId).max().orElse(0) + 1;
        task.setId(id);
        toDos.add(task);
        return task;
    }

    @Override
    public List<Task> findAll(int offset, int limit) {
        List<Task> records = toDos.stream().filter(task -> task.getId() > limit).toList();
        if (offset > records.size()) {
            return  records;
        }
        return records.subList(0, offset);
    }

    @Override
    public Task updateTask(Task task, Long id) {
        Task selectedTask = toDos.stream().filter(toDo -> toDo.getId() == id).toList().getFirst();
        selectedTask.setText(task.getText());
        selectedTask.setPriority(task.getPriority());
        selectedTask.setDueDate(task.getDueDate());
        return selectedTask;
    }

    @Override
    public Task undoneTask(Long id) {
        Task selectedTask = toDos.stream().filter(toDo -> toDo.getId() == id).toList().getFirst();
        if (selectedTask.isState()) {
            selectedTask.setState(false);
            selectedTask.setDoneDate(null);
        }
        return selectedTask;
    }

    @Override
    public Task doneTask(Long id) {
        Task selectedTask = toDos.stream().filter(toDo -> toDo.getId() == id).toList().getFirst();
        if (!selectedTask.isState()) {
            selectedTask.setState(true);
            selectedTask.setDoneDate(new Date());
        }
        return selectedTask;
    }
}

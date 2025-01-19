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
        toDos.add(new Task(1L, null, false, null, null, Task.Priority.HIGH, "Create the repository"));
        toDos.add(new Task(2L, null, false, null, null, Task.Priority.MEDIUM, "Create the spring project"));
        toDos.add(new Task(3L, null, false, null, null, Task.Priority.HIGH, "Make the endpoints"));
        toDos.add(new Task(4L, null, false, null, null, Task.Priority.MEDIUM, "Test the endpoints"));
        toDos.add(new Task(5L, new Date(), true, null, null, Task.Priority.HIGH, "Push commits"));
    }

    @Override
    public Task save(Task task) {
        Long id = toDos.stream().mapToLong(Task::getId).max().orElse(0) + 1;
        task.setId(id);
        toDos.add(task);
        return task;
    }

    @Override
    public List<Task> findAll(int offset, int limit, String priority, String state, String text) {
        List <Task> filterRecords = toDos;
        if (priority.compareTo("all") != 0) {
            filterRecords = filterRecords.stream().filter(task -> task.getPriority().compareTo(Task.Priority.valueOf(priority)) == 0).toList();
        }
        if (state.compareTo("all") != 0) {
            boolean stateInBoolean = state.compareTo("true") == 0;
            filterRecords = filterRecords.stream().filter(task -> task.isState() == stateInBoolean).toList();
        }
        if (text.compareTo("") != 0) {
            filterRecords = filterRecords.stream().filter(task -> task.getText().toLowerCase().contains(text.toLowerCase())).toList();
        }

        filterRecords = filterRecords.stream().filter(task -> task.getId() > offset).toList();
        if (limit > filterRecords.size()) {
            return  filterRecords;
        }
        return filterRecords.subList(0, limit);
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

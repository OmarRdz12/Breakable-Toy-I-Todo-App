package com.encora.backend.dao;

import com.encora.backend.model.CustomResponse;
import com.encora.backend.model.Task;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ToDoDaoImpl implements ToDoDao{
    private static final List<Task> toDos = new ArrayList<>();

    static {
        toDos.add(new Task(1L, null, false, LocalDate.of(2024,9, 12), null, Task.Priority.HIGH, "Create the repository"));
        toDos.add(new Task(2L, null, false, LocalDate.of(2024,9, 12), null, Task.Priority.MEDIUM, "Create the spring project"));
        toDos.add(new Task(3L, null, false, LocalDate.of(2024,9, 12), null, Task.Priority.HIGH, "Make the endpoints"));
        toDos.add(new Task(4L, null, false, LocalDate.of(2024,9, 12), null, Task.Priority.MEDIUM, "Test the endpoints"));
        toDos.add(new Task(5L, LocalDate.now(), true, LocalDate.now(), null, Task.Priority.HIGH, "Push commits"));
    }

    @Override
    public Task save(Task task) {
        Long id = toDos.stream().mapToLong(Task::getId).max().orElse(0) + 1;
        task.setId(id);
        toDos.add(task);
        return task;
    }

    @Override
    public CustomResponse<Task> findAll(int page, int limit, String priority, String state, String name) {
        List <Task> filterRecords = toDos;
        if (priority.compareTo("all") != 0) {
            filterRecords = filterRecords.stream().filter(task -> task.getPriority().compareTo(Task.Priority.valueOf(priority)) == 0).toList();
        }
        if (state.compareTo("all") != 0) {
            boolean stateInBoolean = state.compareTo("true") == 0;
            filterRecords = filterRecords.stream().filter(task -> task.isState() == stateInBoolean).toList();
        }
        if (name.compareTo("") != 0) {
            filterRecords = filterRecords.stream().filter(task -> task.getName().toLowerCase().contains(name.toLowerCase())).toList();
        }

        int total = filterRecords.size();
        int offset = (page - 1) * limit;

        if (offset >= total) {
            offset = Math.max(total - limit, 0);
        }

        int start = Math.min(offset, total);
        int end = Math.min(start + limit, total);
        filterRecords = filterRecords.subList(start, end);

        return new CustomResponse<Task>(filterRecords, total, page);
    }

    @Override
    public Task updateTask(Task task, Long id) {
        Task selectedTask = toDos.stream().filter(toDo -> toDo.getId() == id).toList().getFirst();
        selectedTask.setName(task.getName());
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
            selectedTask.setDoneDate(LocalDate.now());
        }
        return selectedTask;
    }
}

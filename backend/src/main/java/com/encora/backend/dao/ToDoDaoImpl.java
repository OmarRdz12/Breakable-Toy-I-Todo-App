package com.encora.backend.dao;

import com.encora.backend.model.Stat;
import com.encora.backend.model.Task;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class ToDoDaoImpl implements ToDoDao{
    private static final List<Task> toDos = new ArrayList<>();


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

    @Override
    public Task updateTask(Task task, Long id) {
        Optional<Task> optionalTask = toDos.stream()
                .filter(toDo -> toDo.getId().equals(id))
                .findFirst();
        if (optionalTask.isEmpty())
            return null;
        Task selectedTask = optionalTask.get();
        selectedTask.setName(task.getName());
        selectedTask.setPriority(task.getPriority());
        selectedTask.setDueDate(task.getDueDate());
        return selectedTask;
    }

    @Override
    public Task undoneTask(Long id) {
        Optional<Task> optionalTask = toDos.stream().filter(toDo -> toDo.getId().equals(id)).findFirst();
        if (optionalTask.isEmpty())
            return null;
        Task selectedTask = optionalTask.get();
        if (selectedTask.isState()) {
            selectedTask.setState(false);
            selectedTask.setDoneDate(null);
        }
        return selectedTask;
    }

    @Override
    public Task doneTask(Long id) {
        Optional<Task> optionalTask = toDos.stream().filter(toDo -> toDo.getId().equals(id)).findFirst();
        if (optionalTask.isEmpty())
            return null;
        Task selectedTask = optionalTask.get();
        if (!selectedTask.isState()) {
            selectedTask.setState(true);
            selectedTask.setDoneDate(LocalDateTime.now());
        }
        return selectedTask;
    }

    @Override
    public Task deleteTask(Long id) {
        Iterator<Task> iterator = toDos.iterator();
        while(iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId().equals(id)) {
                iterator.remove();
                return task;
            }
        }
        return null;
    }

    public String getAverage(long time, int size) {
        if (time == 0 || size == 0) return "00:00:00";
        long averageTime = Math.ceilDiv(time, size);
        long hours = averageTime / 3600;
        long minutes = (averageTime % 3600) / 60;
        long seconds = averageTime % 60;
        return "".concat(Long.toString(hours)).concat(":").concat(Long.toString(minutes)).concat(":").concat(Long.toString(seconds));
    }

    @Override
    public Stat getStats() {
        long allSeconds = 0L, highSeconds = 0L, mediumSeconds = 0L, lowSeconds = 0L;
        int allDone = 0, doneHighPriority = 0, doneMediumPriority = 0, doneLowPriority = 0;
        for (Task todo: toDos) {
            if (todo.isState()) {
              Duration duration = Duration.between(todo.getCreationDate(), todo.getDoneDate());
              long seconds = duration.getSeconds();
              allSeconds += seconds;
              allDone++;
              if (todo.getPriority().compareTo(Task.Priority.HIGH) == 0) {
                  highSeconds += seconds;
                  doneHighPriority++;
                  continue;
              }
              if (todo.getPriority().compareTo(Task.Priority.MEDIUM) == 0) {
                  mediumSeconds += seconds;
                  doneMediumPriority++;
                  continue;
              }
              if (todo.getPriority().compareTo(Task.Priority.LOW) == 0) {
                  lowSeconds += seconds;
                  doneLowPriority++;
              }
            }
        }
        return new Stat(getAverage(allSeconds, allDone), getAverage(highSeconds, doneHighPriority), getAverage(mediumSeconds, doneMediumPriority), getAverage(lowSeconds, doneLowPriority));
    }
}

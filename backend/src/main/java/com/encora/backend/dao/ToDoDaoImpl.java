package com.encora.backend.dao;

import com.encora.backend.model.CustomResponse;
import com.encora.backend.model.Stat;
import com.encora.backend.model.Task;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Repository
public class ToDoDaoImpl implements ToDoDao{
    private static final List<Task> toDos = new ArrayList<>();

    static {
        toDos.add(new Task(1L, null, false, LocalDate.of(2025, 1, 30), Task.Priority.HIGH, "Create the repository"));
        toDos.add(new Task(2L, null, false, LocalDate.of(2025, 1, 30), Task.Priority.MEDIUM, "Create the spring project"));
        toDos.add(new Task(3L, null, false, LocalDate.of(2025, 1, 30), Task.Priority.HIGH, "Make the endpoints"));
        toDos.add(new Task(4L, LocalDateTime.of(2025, 1, 26, 22, 51, 0), true, LocalDate.of(2025, 1, 30), Task.Priority.MEDIUM, "Test the endpoints"));
        toDos.add(new Task(5L, LocalDateTime.of(2025, 1, 28, 11, 15, 0), true, LocalDate.of(2025, 1, 30), Task.Priority.HIGH, "Push commits"));
    }

    @Override
    public Task save(Task task) {
        Long id = toDos.stream().mapToLong(Task::getId).max().orElse(0) + 1;
        task.setId(id);
        toDos.add(task);
        return task;
    }

    @Override
    public CustomResponse<Task> findAll(int page, int limit, String priority, String state, String name, String dueDateSort, String prioritySort) {
        List <Task> filterRecords = toDos;

        try {
            if (priority.compareTo("all") != 0) {
                filterRecords = new ArrayList<Task>(filterRecords.stream().filter(task -> task.getPriority().compareTo(Task.Priority.valueOf(priority)) == 0).toList());
            }
            if (state.compareTo("all") != 0) {
                boolean stateInBoolean = state.compareTo("true") == 0;
                filterRecords = new ArrayList<Task>(filterRecords.stream().filter(task -> task.isState() == stateInBoolean).toList());
            }
            if (name.compareTo("") != 0) {
                filterRecords = new ArrayList<Task>(filterRecords.stream().filter(task -> task.getName().toLowerCase().contains(name.toLowerCase())).toList());
            }

            if (prioritySort.compareTo("") == 0 && dueDateSort.compareTo("") == 0) {
                filterRecords.sort(Comparator.comparing(Task::getId));
            }
            if ((prioritySort.compareTo("asc") == 0 && dueDateSort.compareTo("asc") == 0)) {
                filterRecords.sort(Comparator.comparing(
                        Task::getPriority).thenComparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))
                );
            }
            if ((prioritySort.compareTo("desc") == 0 && dueDateSort.compareTo("desc") == 0)) {
                filterRecords.sort(Comparator.comparing(
                        Task::getPriority, Comparator.reverseOrder()).thenComparing(Task::getDueDate, Comparator.nullsLast(Comparator.reverseOrder()))
                );
            }
            if ((prioritySort.compareTo("asc") == 0 && dueDateSort.compareTo("desc") == 0)) {
                filterRecords.sort(Comparator.comparing(
                        Task::getPriority).thenComparing(Task::getDueDate, Comparator.nullsLast(Comparator.reverseOrder()))
                );
            }
            if ((prioritySort.compareTo("desc") == 0 && dueDateSort.compareTo("asc") == 0)) {
                filterRecords.sort(Comparator.comparing(
                        Task::getPriority, Comparator.reverseOrder()).thenComparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))
                );
            }
            if ((prioritySort.compareTo("") == 0 && dueDateSort.compareTo("asc") == 0)) {
                filterRecords.sort(Comparator.comparing(
                        Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))
                );
            }
            if ((prioritySort.compareTo("asc") == 0 && dueDateSort.compareTo("") == 0)) {
                filterRecords.sort(Comparator.comparing(
                        Task::getPriority)
                );
            }
            if ((prioritySort.compareTo("") == 0 && dueDateSort.compareTo("desc") == 0)) {
                filterRecords.sort(Comparator.comparing(
                        Task::getDueDate, Comparator.nullsLast(Comparator.reverseOrder()))
                );
            }
            if ((prioritySort.compareTo("desc") == 0 && dueDateSort.compareTo("") == 0)) {
                filterRecords.sort(Comparator.comparing(
                        Task::getPriority, Comparator.reverseOrder())
                );
            }



            int total = filterRecords.size();
            int offset = (page - 1) * limit;

            if (offset >= total) {
                offset = Math.max(total - limit, 0);
            }

            int start = Math.min(offset, total);
            int end = Math.min(start + limit, total);
            filterRecords = filterRecords.subList(start, end);
            return new CustomResponse<>(filterRecords, total, page);
        } catch(UnsupportedOperationException e) {
            e.printStackTrace();
            return new CustomResponse<>(new ArrayList<Task>(), 0, 0);
        }


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
        Task selectedTask = toDos.stream().filter(toDo -> toDo.getId().equals(id)).toList().getFirst();
        if (selectedTask.isState()) {
            selectedTask.setState(false);
            selectedTask.setDoneDate(null);
        }
        return selectedTask;
    }

    @Override
    public Task doneTask(Long id) {
        Task selectedTask = toDos.stream().filter(toDo -> toDo.getId().equals(id)).toList().getFirst();
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

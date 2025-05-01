package com.encora.backend.service;

import com.encora.backend.dao.ToDoDao;
import com.encora.backend.exception.TaskException;
import com.encora.backend.model.CustomResponse;
import com.encora.backend.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService{
    ToDoDao toDoDao;

    public ToDoServiceImpl(ToDoDao toDoDao) {
        this.toDoDao = toDoDao;
    }

    public List<Task> filterToDos(List<Task> toDos, String priority, String name, String state) {
        List<Task> filterToDos = toDos;
        if (!priority.equals("all")) {
            filterToDos = new ArrayList<Task>(filterToDos.stream().filter(task -> task.getPriority().compareTo(Task.Priority.valueOf(priority)) == 0).toList());
        }
        if (!state.equals("all")) {
            boolean stateInBoolean = state.compareTo("true") == 0;
            filterToDos = new ArrayList<Task>(filterToDos.stream().filter(task -> task.isState() == stateInBoolean).toList());
        }
        if (!name.isEmpty()) {
            filterToDos = new ArrayList<Task>(filterToDos.stream().filter(task -> task.getName().toLowerCase().contains(name.toLowerCase())).toList());
        }
        return filterToDos;
    }

    public List<Task> sortToDos(List<Task> toDos, String dueDateSort, String prioritySort) {
        List<Task> sortedToDos = toDos;
        Comparator<Task> comparator = Comparator.comparing(Task::getId);

        boolean sortByPriority = !prioritySort.isEmpty();
        boolean sortByDueDate = !dueDateSort.isEmpty();

        if (sortByPriority && sortByDueDate) {
            Comparator<Task> priorityComparator = prioritySort.equalsIgnoreCase("asc")
                    ? Comparator.comparing(Task::getPriority)
                    : Comparator.comparing(Task::getPriority, Comparator.reverseOrder());

            Comparator<Task> dueDateComparator = dueDateSort.equalsIgnoreCase("asc")
                    ? Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))
                    : Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.reverseOrder()));

            comparator = priorityComparator.thenComparing(dueDateComparator);
        } else if (sortByPriority) {
            comparator = prioritySort.equalsIgnoreCase("asc")
                    ? Comparator.comparing(Task::getPriority)
                    : Comparator.comparing(Task::getPriority, Comparator.reverseOrder());
        } else if (sortByDueDate) {
            comparator = dueDateSort.equalsIgnoreCase("asc")
                    ? Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))
                    : Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.reverseOrder()));
        }

        sortedToDos.sort(comparator);
        return sortedToDos;
    }

    @Override
    public CustomResponse<Task> getAllToDos(int page, int limit, String priority, String state, String name, String dueDateSort, String prioritySort) {
        List<Task> toDos = toDoDao.findAll();
        if (toDos.isEmpty())
            throw new TaskException("There are no tasks to show");

        toDos = filterToDos(toDos, priority, name, state);
        toDos = sortToDos(toDos, dueDateSort, prioritySort);
        int total = toDos.size();
        int offset = (page - 1) * limit;

        if (offset >= total) {
            offset = Math.max(total - limit, 0);
        }

        int start = Math.min(offset, total);
        int end = Math.min(start + limit, total);
        toDos = toDos.subList(start, end);
        return new CustomResponse<>(toDos, total, page);
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

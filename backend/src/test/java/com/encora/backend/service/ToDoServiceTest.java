package com.encora.backend.service;

import com.encora.backend.dao.ToDoDao;
import com.encora.backend.exception.TaskException;
import com.encora.backend.model.CustomResponse;
import com.encora.backend.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {

    @Mock
    private ToDoDao toDoDao;
    @InjectMocks
    private ToDoServiceImpl toDoService;

    private List<Task> taskList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        taskList = new ArrayList<>();
        taskList.add(new Task(1L,null, false, LocalDate.of(2025, 10, 9), Task.Priority.LOW, "Test one"));
        taskList.add(new Task(2L, LocalDateTime.of(2025, 1, 28, 10, 2), true, LocalDate.of(2025, 10, 11), Task.Priority.LOW, "Test two"));
        taskList.add(new Task(3L,null, false, LocalDate.of(2025, 10, 10), Task.Priority.HIGH, "Test three"));
        taskList.add(new Task(4L,LocalDateTime.of(2025, 10, 12, 1, 1), true, LocalDate.of(2025, 10, 12), Task.Priority.MEDIUM, "Test four"));
    }

    @Test
    public void filterToDos_withoutFilters_shouldReturnAllToDos() {
        List<Task> allToDos = toDoService.filterToDos(taskList, "all", "", "all");
        Assertions.assertEquals(4, allToDos.size());
    }

    @Test
    public void filterToDos_byPriority_shouldReturnMatching() {
        List<Task> result = toDoService.filterToDos(taskList, "LOW", "", "all");
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void filterToDos_byState_shouldReturnMatching() {
        List<Task> result = toDoService.filterToDos(taskList, "all", "", "true");
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void filterToDos_byName_shouldReturnMatching() {
        List<Task> result = toDoService.filterToDos(taskList, "all", "test four", "all");
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void filterToDos_noMatches_shouldReturnEmptyList() {
        List<Task> result = toDoService.filterToDos(taskList, "LOW", "five", "true");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void sortToDos_byDueDateAsc() {
        List<Task> sorted = toDoService.sortToDos(new ArrayList<>(taskList), "asc", "");

        Assertions.assertEquals(1L, sorted.get(0).getId());
        Assertions.assertEquals(3L, sorted.get(1).getId());
        Assertions.assertEquals(2L, sorted.get(2).getId());
        Assertions.assertEquals(4L, sorted.get(3).getId());
    }

    @Test
    public void sortToDos_byDueDateDesc() {
        List<Task> sorted = toDoService.sortToDos(new ArrayList<>(taskList), "desc", "");

        Assertions.assertEquals(4L, sorted.get(0).getId());
        Assertions.assertEquals(2L, sorted.get(1).getId());
        Assertions.assertEquals(3L, sorted.get(2).getId());
        Assertions.assertEquals(1L, sorted.get(3).getId());
    }

    @Test
    public void sortToDos_byPriorityAsc() {
        List<Task> sorted = toDoService.sortToDos(new ArrayList<>(taskList), "", "asc");

        Assertions.assertEquals(3L, sorted.get(0).getId());
        Assertions.assertEquals(4L, sorted.get(1).getId());
        Assertions.assertEquals(1L, sorted.get(2).getId());
        Assertions.assertEquals(2L, sorted.get(3).getId());
    }

    @Test
    public void sortToDos_byPriorityDesc() {
        List<Task> sorted = toDoService.sortToDos(new ArrayList<>(taskList), "", "desc");

        Assertions.assertEquals(1L, sorted.get(0).getId());
        Assertions.assertEquals(2L, sorted.get(1).getId());
        Assertions.assertEquals(4L, sorted.get(2).getId());
        Assertions.assertEquals(3L, sorted.get(3).getId());
    }

    @Test
    public void sortToDos_byPriorityAndDueDateAsc() {
        List<Task> sorted = toDoService.sortToDos(new ArrayList<>(taskList), "asc", "asc");

        Assertions.assertEquals(3L, sorted.get(0).getId());
        Assertions.assertEquals(4L, sorted.get(1).getId());
        Assertions.assertEquals(1L, sorted.get(2).getId());
        Assertions.assertEquals(2L, sorted.get(3).getId());
    }

    @Test
    public void save() {
        Task expected = new Task(8L, null, false, LocalDate.of(2025, 2,10), Task.Priority.HIGH, "Test Task");
        Mockito.when(toDoDao.save(expected)).thenReturn(expected);
        final Task result = toDoService.save(expected);
        Assertions.assertEquals(expected.getId(), result.getId());
        Assertions.assertEquals(expected.getName(), result.getName());
        Assertions.assertEquals(expected.getDueDate(), result.getDueDate());

    }

    @Test
    public void updateTask() {
        Task expected = new Task(8L, null, false, LocalDate.of(2025, 2,10), Task.Priority.HIGH, "Test Task");
        Mockito.when(toDoDao.updateTask(expected, 8L)).thenReturn(expected);
        final Task result = toDoService.updateTask(expected, 8L);
        Assertions.assertEquals(expected.getId(), result.getId());
        Assertions.assertEquals(expected.getName(), result.getName());
        Assertions.assertEquals(expected.getDueDate(), result.getDueDate());
        Assertions.assertEquals(expected.getPriority(), result.getPriority());
    }

    @Test
    public void undoneTask() {
        Task expected = new Task(2L, null, false, LocalDate.of(2025, 2,11), Task.Priority.LOW, "task two");
        Mockito.when(toDoDao.undoneTask(2L)).thenReturn(expected);
        final Task result = toDoService.undoneTask( 2L);
        Assertions.assertEquals(expected.getId(), result.getId());
        Assertions.assertEquals(expected.getDoneDate(), result.getDoneDate());
        Assertions.assertEquals(expected.isState(), result.isState());
    }

    @Test
    public void doneTask() {
        Task expected = new Task(3L,LocalDateTime.now(), true, LocalDate.of(2025, 10, 10), Task.Priority.LOW, "Test three");
        Mockito.when(toDoDao.doneTask(3L)).thenReturn(expected);
        final Task result = toDoService.doneTask( 3L);
        Assertions.assertEquals(expected.getId(), result.getId());
        Assertions.assertEquals(expected.getDoneDate(), result.getDoneDate());
        Assertions.assertEquals(expected.isState(), result.isState());
    }

    @Test
    public void deleteTask() {
        Task expected = new Task(2L, LocalDateTime.of(2025, 1, 28, 10, 2), true, LocalDate.of(2025, 10, 11), Task.Priority.LOW, "Test two");
        Mockito.when(toDoDao.deleteTask(2L)).thenReturn(expected);
        final Task result = toDoService.deleteTask( 2L);
        Assertions.assertEquals(expected.getId(), result.getId());
        boolean deleted = taskList.contains(result);
        Assertions.assertFalse(deleted);
    }

    @Test
    public void getAllToDos_shouldThrowExceptionIfEmpty() {
        Mockito.when(toDoDao.findAll()).thenReturn(List.of());

        Assertions.assertThrows(TaskException.class, () -> {
            toDoService.getAllToDos(1, 10, "all", "all", "", "", "");
        });
    }

    @Test
    public void shouldFilterByStateTrueAndPriorityLOW() {
        Mockito.when(toDoDao.findAll()).thenReturn(taskList);
        CustomResponse<Task> response = toDoService.getAllToDos(1, 10, "LOW", "true", "", "", "");
        Assertions.assertEquals(1, response.getData().size());
        Assertions.assertEquals("Test two", response.getData().getFirst().getName());
    }

}

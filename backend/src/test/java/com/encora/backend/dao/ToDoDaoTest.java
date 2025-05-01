package com.encora.backend.dao;

import com.encora.backend.model.Stat;
import com.encora.backend.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class ToDoDaoTest {
    private ToDoDaoImpl toDoDao;

    @BeforeEach
    public void setUp() {
        toDoDao = new ToDoDaoImpl();
        toDoDao.findAll().clear();
    }

    @Test
    public void save() {
        Task savedTask = toDoDao.save(new Task(2L, null, false, null, Task.Priority.LOW, "test"));
        Assertions.assertNotNull(savedTask.getId());
        Assertions.assertEquals("test", savedTask.getName());
        Assertions.assertFalse(savedTask.isState());
        System.out.println(savedTask.getId());
    }

    @Test
    public void updateTask() {
        Task original = new Task( 1L, null, false, LocalDate.of(2025, 1, 28), Task.Priority.HIGH, "original name");
        Task saved = toDoDao.save(original);
        Task update = new Task(1L, null, false, LocalDate.of(2025, 1, 30), Task.Priority.LOW, "test edited");

        Task result = toDoDao.updateTask(update, saved.getId());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals(LocalDate.of(2025, 1, 30), result.getDueDate());
        Assertions.assertEquals("test edited", result.getName());
        Assertions.assertEquals(Task.Priority.LOW, result.getPriority());
    }

    @Test
    public void updateTask_emptyTodos_shouldReturnNul() {
        Task update = new Task(1L, null, false, LocalDate.of(2025, 1, 30), Task.Priority.LOW, "test edited");
        Task result = toDoDao.updateTask(update, 1L);
        Assertions.assertNull(result);
    }

    @Test
    public void doneTask() {
        Task original = new Task( 1L, null, false, LocalDate.of(2025, 1, 28), Task.Priority.HIGH, "original name");
        Task saved = toDoDao.save(original);
        Task doneTask = toDoDao.doneTask(saved.getId());
        Assertions.assertNotNull(doneTask.getId());
        Assertions.assertNotNull(doneTask.getDoneDate());
        Assertions.assertTrue(doneTask.isState());
    }

    @Test
    public void doneTask_emptyTodos_shouldReturnNul() {
        Task result = toDoDao.doneTask(1L);
        Assertions.assertNull(result);
    }

    @Test
    public void undoneTask() {
        Task original = new Task( 1L, null, true, LocalDate.of(2025, 1, 28), Task.Priority.HIGH, "original name");
        Task saved = toDoDao.save(original);
        Task updateTask = toDoDao.undoneTask(saved.getId());
        Assertions.assertNotNull(updateTask.getId());
        Assertions.assertNull(updateTask.getDoneDate());
        Assertions.assertFalse(updateTask.isState());
    }

    @Test
    public void undoneTask_emptyTodos_shouldReturnNul() {
        Task result = toDoDao.undoneTask(1L);
        Assertions.assertNull(result);
    }

    @Test
    public void deleteTask() {
        Task original = new Task( 1L, null, true, LocalDate.of(2025, 1, 28), Task.Priority.HIGH, "original name");
        Task saved = toDoDao.save(original);
        Task deletedTask = toDoDao.deleteTask(saved.getId());
        Assertions.assertNotNull(deletedTask.getId());
    }

    @Test
    public void getAverageNull() {
        String average = toDoDao.getAverage(0, 0);
        Assertions.assertEquals("00:00:00", average);
    }

    @Test
    public void getAverageTimeZero() {
        String average = toDoDao.getAverage(0, 1);
        Assertions.assertEquals("00:00:00", average);
    }

    @Test
    public void getAverageSizeZero() {
        String average = toDoDao.getAverage(60, 0);
        Assertions.assertEquals("00:00:00", average);
    }


    @Test
    public void getAverageWell() {
        String average = toDoDao.getAverage(60, 1);
        Assertions.assertEquals("0:1:0", average);
    }

    @Test
    public void getStats() {
        Stat stats = toDoDao.getStats();
        Assertions.assertNotNull(stats.getAllDoneStats());
        Assertions.assertNotNull(stats.getHighDoneStats());
        Assertions.assertNotNull(stats.getMediumDoneStats());
        Assertions.assertNotNull(stats.getLowDoneStats());
    }

    @Test
    public void testGetStats_MultipleTasksAverageTime() {
        toDoDao.findAll().clear();

        Task high = new Task(1L, LocalDateTime.of(2025, 4, 1, 10, 10), true, LocalDate.now(), Task.Priority.HIGH, "High Task");
        high.setCreationDate(LocalDateTime.of(2025, 4, 1, 10, 0));
        toDoDao.save(high);

        Task medium = new Task(2L, LocalDateTime.of(2025, 4, 1, 11, 5), true, LocalDate.now(), Task.Priority.MEDIUM, "Medium Task");
        medium.setCreationDate(LocalDateTime.of(2025, 4, 1, 11, 0));
        toDoDao.save(medium);

        Task low = new Task(3L, LocalDateTime.of(2025, 4, 1, 12, 15), true, LocalDate.now(), Task.Priority.LOW, "Low Task");
        low.setCreationDate(LocalDateTime.of(2025, 4, 1, 12, 0));
        toDoDao.save(low);

        Stat stats = toDoDao.getStats();

        Assertions.assertEquals("0:10:0", stats.getAllDoneStats());

        Assertions.assertEquals("0:10:0", stats.getHighDoneStats());
        Assertions.assertEquals("0:5:0", stats.getMediumDoneStats());
        Assertions.assertEquals("0:15:0", stats.getLowDoneStats());
    }






}


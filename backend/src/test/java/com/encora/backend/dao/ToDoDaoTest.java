package com.encora.backend.dao;

import com.encora.backend.model.CustomResponse;
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
        Task update = new Task(2L, null, false, LocalDate.of(2025, 1, 30), Task.Priority.LOW, "test edited");
        Task updateTask = toDoDao.updateTask(update, 2L);
        Assertions.assertNotNull(updateTask.getId());
        Assertions.assertEquals(LocalDate.of(2025, 1, 30), updateTask.getDueDate());
        Assertions.assertEquals("test edited", updateTask.getName());
        Assertions.assertFalse(updateTask.isState());
    }

    @Test
    public void doneTask() {
        Task updateTask = toDoDao.doneTask(2L);
        Assertions.assertNotNull(updateTask.getId());
        Assertions.assertNotNull(updateTask.getDoneDate());
        Assertions.assertTrue(updateTask.isState());
    }

    @Test
    public void undoneTask() {
        Task updateTask = toDoDao.undoneTask(2L);
        Assertions.assertNotNull(updateTask.getId());
        Assertions.assertNull(updateTask.getDoneDate());
        Assertions.assertFalse(updateTask.isState());
    }

    @Test
    public void deleteTask() {
        Task udateTask = toDoDao.deleteTask(2L);
        Assertions.assertNotNull(udateTask.getId());
        Task alreadyUpdated = toDoDao.deleteTask(2L);
        Assertions.assertNull(alreadyUpdated);
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
    public void getAllRecords() {
        CustomResponse<Task> response = toDoDao.findAll(1, 10, "all", "all", "", "", "");
        Assertions.assertEquals(5, response.getPages());
        Assertions.assertEquals(1, response.getOffset());
        Assertions.assertNotNull(response.getData());

    }

    @Test
    public void getPriorityRecords() {
        CustomResponse<Task> response = toDoDao.findAll(1, 10, "HIGH", "all", "", "", "");
        Assertions.assertEquals(2, response.getPages());
        Assertions.assertEquals(1, response.getOffset());
        Assertions.assertNotNull(response.getData());
    }

    @Test
    public void getStateRecords() {
        CustomResponse<Task> response = toDoDao.findAll(1, 10, "all", "true", "", "", "");
        Assertions.assertEquals(3, response.getPages());
        Assertions.assertEquals(1, response.getOffset());
        Assertions.assertNotNull(response.getData());
    }

    @Test
    public void getNameRecords() {
        CustomResponse<Task> response = toDoDao.findAll(1, 10, "all", "all", "push", "", "");
        Assertions.assertEquals(1, response.getPages());
        Assertions.assertEquals(1, response.getOffset());
        Assertions.assertNotNull(response.getData());

    }

    @Test
    public void getPrioritySortRecords() {
        CustomResponse<Task> response = toDoDao.findAll(1, 10, "all", "all", "", "", "asc");
        Assertions.assertEquals(6, response.getPages());
        Assertions.assertEquals(1, response.getOffset());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Create the repository", response.getData().getFirst().getName());

    }

    @Test
    public void getPrioritySortDescRecords() {
        CustomResponse<Task> response = toDoDao.findAll(1, 10, "all", "all", "", "", "desc");
        Assertions.assertEquals(6, response.getPages());
        Assertions.assertEquals(1, response.getOffset());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("test edited", response.getData().getFirst().getName());

    }

    @Test
    public void getDueDateSortRecords() {
        CustomResponse<Task> response = toDoDao.findAll(1, 10, "all", "all", "", "asc", "");
        Assertions.assertEquals(6, response.getPages());
        Assertions.assertEquals(1, response.getOffset());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("Create the repository", response.getData().getFirst().getName());

    }

    @Test
    public void getDueDateSortDescRecords() {
        Task update = toDoDao.updateTask(new Task(2L, null, false, LocalDate.of(2025, 3, 30), Task.Priority.LOW, "test edited"), 2L);
        CustomResponse<Task> response = toDoDao.findAll(1, 10, "all", "all", "", "desc", "");
        Assertions.assertEquals(5, response.getPages());
        Assertions.assertEquals(1, response.getOffset());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("test edited", response.getData().getFirst().getName());

    }





}


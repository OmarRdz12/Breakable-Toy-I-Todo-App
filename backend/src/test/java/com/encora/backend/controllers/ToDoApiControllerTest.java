package com.encora.backend.controllers;

import com.encora.backend.exception.TaskException;
import com.encora.backend.model.Task;
import com.encora.backend.service.ToDoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(ToDoApiController.class)
public class ToDoApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoServiceImpl toDoService;

    @InjectMocks
    private ToDoApiController toDoController;

    @Autowired
    private ObjectMapper objectMapper;

    private Task newTask;

    @BeforeEach
    public void setUp() {
        newTask = new Task(1L, LocalDateTime.now(), true, LocalDate.of(2025, 10, 9), Task.Priority.LOW, "Test one");
    }

    @Test
    public void createTodo() throws  Exception {
        Task created = new Task(8L, null, false, LocalDate.of(2025, 2,10), Task.Priority.HIGH, "Test Task");
        Mockito.when(toDoService.save(created)).thenReturn(created);
        mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createTodo_withInvalidData_shouldReturnBadRequest() throws Exception {
        Task invalidTask = new Task(1L, null, false, null, Task.Priority.LOW, "");
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTask)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void getTodos() throws Exception {
        mockMvc.perform(get("/todos")
                        .param("page", "1")
                        .param("limit", "10"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTodos_withInvalidPage_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/todos")
                        .param("page", "0")
                        .param("limit", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getTodos_withInvalidLimit_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/todos")
                        .param("page", "1")
                        .param("limit", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTodo() throws  Exception {
        Task updated = new Task(1L, null, false, LocalDate.of(2025, 2,15), Task.Priority.LOW, "Test Task edited");
        Mockito.when(toDoService.updateTask(Mockito.any(Task.class), Mockito.eq(1L))).thenReturn(updated);
        mockMvc.perform(put("/todos/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updated))).andExpect(status().isOk());
    }

    @Test
    public void updateTodo_withNullId_shouldThrowException() {
        Task updatedTask = new Task(1L, null, false, null, Task.Priority.LOW, "TASK");
        Exception exception = Assertions.assertThrows(TaskException.class, () -> {
            toDoController.updateTodo(updatedTask, null);
        });

        Assertions.assertEquals("ID cannot be null", exception.getMessage());
    }

    @Test
    public void undoneTodo() throws  Exception {

        Mockito.when(toDoService.undoneTask(1L)).thenAnswer(invocation -> {
            if (newTask.isState()) {
                newTask.setState(false);
                newTask.setDoneDate(null);
            }
            return newTask;
        });

        ResponseEntity<Task> result = toDoController.undoneTodo(1L);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertFalse(result.getBody().isState());
        Assertions.assertNull(result.getBody().getDoneDate());
    }

    @Test
    public void undoneTodo_withNullId_shouldThrowException() {
        Exception exception = Assertions.assertThrows(TaskException.class, () -> {
            toDoController.undoneTodo(null);
        });

        Assertions.assertEquals("ID cannot be null", exception.getMessage());
    }

    @Test
    public void doneTodo() throws  Exception {

        Mockito.when(toDoService.doneTask(1L)).thenAnswer(invocation -> {
            if (!newTask.isState()) {
                newTask.setState(true);
                newTask.setDoneDate(LocalDateTime.now());
            }
            return newTask;
        });

        ResponseEntity<Task> result = toDoController.doneTodo(1L);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertTrue(result.getBody().isState());
        Assertions.assertNotNull(result.getBody().getDoneDate());
    }

    @Test
    public void doneTodo_withNullId_shouldThrowException() {
        Exception exception = Assertions.assertThrows(TaskException.class, () -> {
            toDoController.doneTodo(null);
        });

        Assertions.assertEquals("ID cannot be null", exception.getMessage());
    }

    @Test
    public void testDeleteToDoEndpoint() throws Exception{
        Mockito.when(toDoService.deleteTask(1L))
                .thenReturn(newTask);

        mockMvc.perform(delete("/todos/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(1L))).andExpect(status().isOk());
    }

}

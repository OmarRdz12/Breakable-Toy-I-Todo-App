package com.encora.backend.controllers;

import com.encora.backend.model.Task;
import com.encora.backend.service.ToDoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


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
    public void updateTodo() throws  Exception {
        Task updated = new Task(1L, null, false, LocalDate.of(2025, 2,15), Task.Priority.LOW, "Test Task edited");
        Mockito.when(toDoService.save(updated)).thenReturn(updated);
        mockMvc.perform(put("/todos/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updated))).andExpect(status().isOk());
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
    public void TestDeleteToDoEndpoint() throws Exception{
        Mockito.when(toDoService.deleteTask(1L))
                .thenReturn(newTask);

        mockMvc.perform(delete("/todos/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(1L))).andExpect(status().isOk());
    }



}

package com.encora.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {

    public enum Priority {
        HIGH,
        MEDIUM,
        LOW
    }

    private Long id;
    private boolean state;
    private LocalDateTime doneDate;
    private LocalDateTime creationDate;
    private LocalDate dueDate;
    @NotNull(message = "Priority cannot be null")
    private Priority priority;
    @NotBlank(message = "Name cannot be null")
    private String name;

    public Task(Long id, LocalDateTime doneDate, boolean state, LocalDate dueDate, Priority priority, String name) {
        this.doneDate = doneDate;
        this.id = id;
        this.state = state;
        this.dueDate = dueDate;
        this.creationDate = LocalDateTime.now();
        this.priority = priority;
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getDoneDate() {
        return doneDate;
    }

    public boolean isState() {
        return state;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setDoneDate(LocalDateTime doneDate) {
        this.doneDate = doneDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setName(String name) {
        this.name = name;
    }
}

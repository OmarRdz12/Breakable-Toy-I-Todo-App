package com.encora.backend.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    private Long id;
    private boolean state;
    private LocalDateTime doneDate;
    private LocalDateTime creationDate;
    private LocalDate dueDate;
    private Priority priority;
    private String name;;

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

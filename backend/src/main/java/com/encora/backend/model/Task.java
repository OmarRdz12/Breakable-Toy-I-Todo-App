package com.encora.backend.model;

import java.time.LocalDate;

public class Task {

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    private Long id;
    private boolean state;
    private LocalDate doneDate;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private Priority priority;
    private String name;

    public Task(Long id, LocalDate doneDate, boolean state, LocalDate dueDate, LocalDate creationDate, Priority priority, String name) {
        this.doneDate = doneDate;
        this.id = id;
        this.state = state;
        this.dueDate = dueDate;
        this.creationDate = creationDate;
        this.priority = priority;
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getDoneDate() {
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

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setName(String text) {
        this.name = name;
    }
}

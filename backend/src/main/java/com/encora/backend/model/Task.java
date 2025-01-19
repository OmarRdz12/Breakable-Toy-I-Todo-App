package com.encora.backend.model;

import java.util.Date;

public class Task {

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    private Long id;
    private boolean state;
    private Date doneDate;
    private Date creationDate;
    private Date dueDate;
    private Priority priority;
    private String text;

    public Task(Long id, Date doneDate, boolean state, Date dueDate, Date creationDate, Priority priority, String text) {
        this.doneDate = doneDate;
        this.id = id;
        this.state = state;
        this.dueDate = dueDate;
        this.creationDate = creationDate;
        this.priority = priority;
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public boolean isState() {
        return state;
    }

    public Long getId() {
        return id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getText() {
        return text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setText(String text) {
        this.text = text;
    }
}

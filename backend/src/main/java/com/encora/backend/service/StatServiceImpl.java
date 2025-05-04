package com.encora.backend.service;

import com.encora.backend.dao.ToDoDao;
import com.encora.backend.model.Stat;
import org.springframework.stereotype.Service;

@Service
public class StatServiceImpl implements StatService{
    ToDoDao toDoDao;

    public StatServiceImpl(ToDoDao toDoDao) {
        this.toDoDao = toDoDao;
    }

    @Override
    public Stat getStats() {
        return toDoDao.getStats();
    }

    @Override
    public Integer getPending() {
        return (int) toDoDao.findAll()
                .stream()
                .filter(task -> !task.isState())
                .count();
    }
}

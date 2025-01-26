package com.encora.backend.controllers;

import com.encora.backend.model.Stat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface StatApi {
    @GetMapping("/todos/stats")
    ResponseEntity<Stat> getStats();
}

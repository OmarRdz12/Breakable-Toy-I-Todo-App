package com.encora.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface pruebaAPI {
    @GetMapping("/hello")
    ResponseEntity<String> helloWorld();
}

package com.encora.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class pruebaAPIController implements pruebaAPI{
    @Override
    public ResponseEntity<String> helloWorld() {
        return new ResponseEntity<>("hola mundo", HttpStatus.OK);
    }
}

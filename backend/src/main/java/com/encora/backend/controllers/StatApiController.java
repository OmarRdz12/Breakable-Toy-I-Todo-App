package com.encora.backend.controllers;

import com.encora.backend.model.Stat;
import com.encora.backend.service.StatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatApiController implements StatApi {
    StatService statService;

    public StatApiController(StatService statService) {
        this.statService = statService;
    }

    @Override
    public ResponseEntity<Stat> getStats() {
        return new ResponseEntity<>(statService.getStats(), HttpStatus.OK);
    }
}

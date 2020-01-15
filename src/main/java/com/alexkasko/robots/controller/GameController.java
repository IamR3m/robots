package com.alexkasko.robots.controller;

import com.alexkasko.robots.entity.MessageLog;
import com.alexkasko.robots.entity.Task;
import com.alexkasko.robots.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {

    private final RestService restService;

    @Autowired
    public GameController(RestService restService) {
        this.restService = restService;
    }

    @GetMapping("/log")
    public @ResponseBody List<MessageLog> readLog() {
        return restService.readLog();
    }

    @PostMapping("/task")
    public @ResponseBody ResponseEntity<String> addTask(@Valid @RequestBody Task task) {
        if(restService.addTask(task)) {
            return new ResponseEntity<String>("ok", HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("error", HttpStatus.BAD_REQUEST);
    }
}

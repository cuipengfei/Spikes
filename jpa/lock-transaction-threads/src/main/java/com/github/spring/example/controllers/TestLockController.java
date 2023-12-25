package com.github.spring.example.controllers;

import com.github.spring.example.service.Problem1Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestLockController {
    private Logger logger = LoggerFactory.getLogger(TestLockController.class);

    @Autowired
    private Problem1Service p1Service;

    @GetMapping("problem1")
    public ResponseEntity<String> problem1() {
        p1Service.problem1();
        return ResponseEntity.ok("problem1");
    }
}

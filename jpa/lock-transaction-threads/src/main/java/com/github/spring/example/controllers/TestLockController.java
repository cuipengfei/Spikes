package com.github.spring.example.controllers;

import com.github.spring.example.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutionException;

@Controller
public class TestLockController {
    private Logger logger = LoggerFactory.getLogger(TestLockController.class);

    @Autowired
    private Problem1Service p1Service;

    @Autowired
    private Problem1FixService p1FixService;

    @Autowired
    private Problem2Service p2Service;

    @Autowired
    private Problem2BadFixService p2BadFixService;

    @Autowired
    private Problem2GoodFixService p2GoodFixService;

    @GetMapping("problem1")
    public ResponseEntity<String> problem1() {
        p1Service.problem1();
        return ResponseEntity.ok("problem1");
    }

    @GetMapping("problem1-fix")
    public ResponseEntity<String> problem1Fix() {
        p1FixService.problem1Fix();
        return ResponseEntity.ok("problem1Fix");
    }

    @GetMapping("problem2")
    public ResponseEntity<String> problem2() {
        p2Service.problem2();
        return ResponseEntity.ok("problem2");
    }

    @GetMapping("problem2-bad-fix")
    public ResponseEntity<String> problem2BadFix() throws ExecutionException, InterruptedException {
        p2BadFixService.problem2BadFix();
        return ResponseEntity.ok("problem2BadFix");
    }

    @GetMapping("problem2-good-fix")
    public ResponseEntity<String> problem2GoodFix() throws ExecutionException, InterruptedException {
        p2GoodFixService.problem2GoodFix();
        return ResponseEntity.ok("problem2GoodFix");
    }
}

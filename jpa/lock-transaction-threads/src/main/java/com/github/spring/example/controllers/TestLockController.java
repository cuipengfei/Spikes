package com.github.spring.example.controllers;

import com.github.spring.example.service.TestLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class TestLockController {
    private Logger logger = LoggerFactory.getLogger(TestLockController.class);

    @Autowired
    private TestLockService service;

    @GetMapping("test-lock")
    public ResponseEntity<String> testLock() {
        logger.info("start");

        Arrays.asList("a", "b", "c").parallelStream().forEach(key -> {
            logger.info("going to call test lock method with key: {}", key);
            service.testLock(key);
        });

        logger.info("end");

        return ResponseEntity.ok("done");
    }
}

package com.github.spring.example.controllers;

import com.github.spring.example.service.RunWorkersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class TestHangThreadController {
    Logger logger = LoggerFactory.getLogger(TestHangThreadController.class);

    @Autowired
    JdbcLockRegistry registry;

    @Autowired
    RunWorkersService runWorkersService;

    @GetMapping("long-running-api")
    public ResponseEntity<String> simulateHangThread(
            @RequestParam(value = "callExpire", required = false) String callExpire) {
        String name = "api " + Thread.currentThread().getId();

        if (Objects.equals(callExpire, "yes")) {
            logger.info(name + " - will call expire before starting new thread");
            registry.expireUnusedOlderThan(10000);
        } else {
            logger.info(name + " - will NOT call expire");
        }

        logger.info(name + " - will start a new thread that gets lock and runs for 3 mins, and does not release lock");
        runWorkersService.runAsyncWorker(name, 180);
        logger.info(name + " - worker started");

        return ResponseEntity.ok("long-running-api");
    }
}

package com.github.spring.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Service
public class RunWorkersService {
    Logger logger = LoggerFactory.getLogger(RunWorkersService.class);
    ExecutorService executor = Executors.newFixedThreadPool(10);

    @Autowired
    LockRegistry registry;

    public void runBoth() throws InterruptedException {
        logger.info("main - going to run worker 1");
        CompletableFuture<?> future1 = runWorker1();

        Thread.sleep(1000); // make sure worker 2 is later enough

        logger.info("main - going to run worker 2");
        CompletableFuture<?> future2 = runWorker2();

        CompletableFuture.allOf(future1, future2).join();
    }

    public CompletableFuture<Void> runWorker2() {
        return CompletableFuture.runAsync(() -> runWorker("worker 2"), executor);
    }

    public CompletableFuture<Void> runWorker1() {
        return CompletableFuture.runAsync(() -> runWorker("worker 1"), executor);
    }

    private void runWorker(String workerName) {
        logger.info(workerName + " - start");

        logger.info(workerName + " - going to lock");

        boolean locked = tryGetLock();
        if (locked) {
            logger.info(workerName + " - lock get ok");
            for (int i = 0; i < 25; i++) {
                try {
                    logger.info(workerName + " - had been running for " + i + " seconds");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            logger.info(workerName + " - does not release the lock");
        } else {
            logger.info(workerName + " - lock get fail");
        }
    }

    private boolean tryGetLock() {
        Lock lock = registry.obtain("string123");
        try {
            return lock.tryLock(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

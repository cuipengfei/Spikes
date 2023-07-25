package com.github.spring.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;

import static java.util.concurrent.CompletableFuture.runAsync;

@Service
public class RunWorkersService {
    Logger logger = LoggerFactory.getLogger(RunWorkersService.class);
    ExecutorService executor = Executors.newFixedThreadPool(10);

    @Autowired
    JdbcLockRegistry registry;

    public void runBoth() {
        logger.info("main - going to run worker 1");
        CompletableFuture<?> future1 = runWorker1();

        logger.info("main - schedule worker 2 to run 26 seconds later, which is later than ttl");
        Executor delay26Executor = CompletableFuture.delayedExecutor(26, TimeUnit.SECONDS, executor);
        CompletableFuture<Void> future2 = runAsync(
                () -> runWorker("worker 2", 25),
                delay26Executor);

        CompletableFuture.allOf(future1, future2).join();
    }

    public void runBothWithProactiveExpire() {
        logger.info("main - going to run worker 1");
        CompletableFuture<?> future1 = runWorker1();

        logger.info("main - schedule worker 2 to run 26 seconds later, which is later than ttl");
        Executor delay26Executor = CompletableFuture.delayedExecutor(26, TimeUnit.SECONDS, executor);
        CompletableFuture<Void> future2 = runAsync(
                () -> {
                    logger.info("going to expireUnusedOlderThan 2000");
                    registry.expireUnusedOlderThan(2000);
                    runWorker("worker 2", 25);
                },
                delay26Executor);

        CompletableFuture.allOf(future1, future2).join();
    }

    public CompletableFuture<Void> runWorker1() {
        return runAsync(() -> runWorker("worker 1", 25), executor);
    }

    public CompletableFuture<Void> runWorker2() {
        return runAsync(() -> runWorker("worker 2", 25), executor);
    }

    public CompletableFuture<Void> runWorker2WithProactiveExpire() {
        return runAsync(() -> {
            logger.info("going to expireUnusedOlderThan 2000");
            logger.info("but if the ttl is not reached yet, then this should have no effect");
            registry.expireUnusedOlderThan(2000);
            runWorker("worker 2", 25);
        }, executor);
    }

    public CompletableFuture<Void> simulateStuckThread() {
        logger.info("going to simulate a stuck worker");

        CompletableFuture<Void> future = runAsync(() -> runWorker("simulated-stuck-worker", 120), executor);
        future.completeOnTimeout(null, 25, TimeUnit.SECONDS)
                .thenRun(() -> {
                    logger.info("worker time out reached");
                    logger.info("worker may not be able to release lock by itself, so we will release for it here");
                    registry.expireUnusedOlderThan(1000);
                    logger.info("expire called");
                })
                .handle((value, ex) -> {
                    if (ex != null) {
                        logger.error("error: ", ex);
                    } else {
                        logger.info("no error");
                    }
                    return null;
                });

        return future;
    }

    private void runWorker(String workerName, int workSeconds) {
        logger.info(workerName + " - start");

        logger.info(workerName + " - going to try to get lock");

        boolean locked = tryGetLock();
        if (locked) {
            logger.info(workerName + " - lock get ok");
            for (int i = 0; i < workSeconds; i++) {
                try {
                    logger.info(workerName + " - had been running for " + i + " seconds");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            logger.info(workerName + " - end, does not release the lock");
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

package com.github.spring.example.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;

import static java.util.concurrent.CompletableFuture.runAsync;

@Service
public class RunWorkersService {
    Logger logger = LoggerFactory.getLogger(RunWorkersService.class);
    ExecutorService executor = Executors.newFixedThreadPool(10);

    @Autowired
    JdbcLockRegistry jdbcLockRegistry;

    @Autowired
    RedisLockRegistry redisLockRegistry;

    @Autowired
    RedissonClient redissonClient;

    @Value("${lock.registry.name}")
    String registryName;

    public void runBothWorkersInSameProcess() {
        logger.info("main - going to run worker 1");
        CompletableFuture<?> future1 = runAsync(() -> runWorker("worker 1", 60));

        logger.info("main - schedule worker 2 to run 15 seconds after worker 1");
        logger.info("main - when worker 1 has ran for 20 secs / worker 2 has ran for 5 secs " +
                "that is when TTL/lease time has arrived");
        Executor delayedExecutor = CompletableFuture.delayedExecutor(15, TimeUnit.SECONDS, executor);
        CompletableFuture<Void> future2 = runAsync(
                () -> runWorker("worker 2", 25),
                delayedExecutor);

        CompletableFuture.allOf(future1, future2).join();
    }

    public void runBothInSameProcessWithProactiveExpire() {
        logger.info("main - going to run worker 1");
        CompletableFuture<?> future1 = runAsync(() -> runWorker("worker 1", 60));

        logger.info("main - schedule worker 2 to run 25 seconds later, which is later than ttl");
        Executor delayedExecutor = CompletableFuture.delayedExecutor(25, TimeUnit.SECONDS, executor);
        CompletableFuture<Void> future2 = runAsync(
                () -> {
                    logger.info("going to call expireUnusedOlderThan(2000) at the start of worker 2");
                    expiredUnusedOlderThan(2000);
                    runWorker("worker 2", 25);
                },
                delayedExecutor);

        CompletableFuture.allOf(future1, future2).join();
    }

    public void runBothInSameProcessWithProactiveExpireBeforeTTL() {
        logger.info("main - going to run worker 1");
        CompletableFuture<?> future1 = runAsync(() -> runWorker("worker 1", 60));

        logger.info("main - schedule worker 2 to run 10 seconds later, which is before ttl");
        Executor delayedExecutor = CompletableFuture.delayedExecutor(10, TimeUnit.SECONDS, executor);
        CompletableFuture<Void> future2 = runAsync(
                () -> {
                    logger.info("going to call expireUnusedOlderThan(2000) at the start of worker 2");
                    expiredUnusedOlderThan(2000);
                    logger.info("called expireUnusedOlderThan(2000) at the start of worker 2");
                    logger.info("but it should have no effect since ttl is not reached yet");
                    runWorker("worker 2", 25);
                },
                delayedExecutor);

        CompletableFuture.allOf(future1, future2).join();
    }

    public CompletableFuture<Void> runWorker2WithProactiveExpire() {
        return runAsync(() -> {
            logger.info("going to expireUnusedOlderThan 2000");
            logger.info("but if the ttl is not reached yet, then this should have no effect");
            expiredUnusedOlderThan(2000);
            runWorker("worker 2", 25);
        }, executor);
    }

    public CompletableFuture<Void> runAsyncWorker(String workerName, int workSeconds) {
        return runAsync(() -> runWorker(workerName, workSeconds), executor);
    }

    private void runWorker(String workerName, int workSeconds) {
        logger.info(workerName + " - start");
        logger.info(workerName + " - going to try to get lock");

        boolean locked = tryGetLock();
        if (locked) {
            logger.info(workerName + " - lock get ok");
            wasteTime(workerName, workSeconds);
            logger.info(workerName + " - end, does not release the lock");
        } else {
            logger.info(workerName + " - lock get fail");
        }
    }


    // 空转，模拟busy work
    private void wasteTime(String workerName, int workSeconds) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + workSeconds * 1000L;
        long lastLogTime = startTime;

        while (System.currentTimeMillis() < endTime) {
            // loop to waste time
            for (int i = 0; i < 4000; i++) {
                for (int j = 0; j < 4000; j++) {
                    doMath();
                }
            }

            // log time elapsed about once per second
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastLogTime >= 1000) {
                double elapsedTime = (currentTime - startTime) / 1000.0;
                logger.info(workerName + " - is running: " + elapsedTime + " seconds");
                lastLogTime = currentTime;
            }
        }
    }

    private String doMath() {
        Random random = new Random();
        int a = random.nextInt(1000000);
        int b = random.nextInt(1000000);
        int c = random.nextInt(1000000);
        int d = random.nextInt(1000000);
        int e = random.nextInt(1000000);
        int f = random.nextInt(1000000);
        int result = (a * b + c * d - e) * f;
        return Integer.toString(result);
    }

    private boolean tryGetLock() {
        try {
            String key = "string1234";

            if (Objects.equals(registryName, "redisson")) {
                RLock redissonLock = redissonClient.getLock(key);
                return redissonLock.tryLock(20, 20, TimeUnit.SECONDS);
            } else {
                Lock lock = getLockRegistry().obtain(key);
                return lock.tryLock(20, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            logger.error("try lock failure", e);
        }

        return false;
    }

    private ExpirableLockRegistry getLockRegistry() {
        if (Objects.equals(registryName, "jdbc")) {
            return jdbcLockRegistry;
        } else {
            return redisLockRegistry;
        }
    }

    private void expiredUnusedOlderThan(int age) {
        if (Objects.equals(registryName, "redisson")) {
            // todo: try to find out this later
        } else {
            getLockRegistry().expireUnusedOlderThan(age);
        }
    }

}

package com.github.spring.example.service;

import com.github.spring.example.CatRepository;
import com.github.spring.example.model.Cat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Service
public class TestLockService {
    @Autowired
    private CatRepository catRepo;

    private Logger logger = LoggerFactory.getLogger(TestLockService.class);

    @Autowired
    private JdbcLockRegistry jdbcLockRegistry;

    @Transactional
    public void testLock() {
        catRepo.findById(UUID.randomUUID());
        Arrays.asList("a", "b", "c").parallelStream().forEach(key -> {
            logger.info("going to call lock method with key: {}", key);
            lock(key);
        });
    }

    @Transactional()
    public void testLock2() {
        Optional<Cat> byId = catRepo.findById(UUID.randomUUID());
        lock(UUID.randomUUID().toString());
    }

    private void lock(String key) {
        boolean isLocked = false;
        Lock lock = jdbcLockRegistry.obtain(key);
        try {
            pretendToDoWork(key);
            // ↑ comment this out to see errors

            isLocked = lock.tryLock(10, TimeUnit.SECONDS);
            if (isLocked) {
                logger.info("{} lock ok", key);
            } else {
                logger.info("{} lock failed", key);
            }
        } catch (Throwable t) {
            logger.error("{} lock failed with exception: {}", key, t.getMessage());
        } finally {
            if (isLocked) {
                logger.info("{} unlock", key);
                lock.unlock();
            } else {
                logger.info("{} skip unlock since it was never locked", key);
            }
        }
    }

    private void pretendToDoWork(String key) throws InterruptedException {
        Random random = new Random();
        int randomNumber = random.nextInt(3000);
        logger.info("{} pretend to work for {} before try to lock", key, randomNumber);
        Thread.sleep(randomNumber);
    }
}

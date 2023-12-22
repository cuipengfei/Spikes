package com.github.spring.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Service
public class TestLockService {
    private Logger logger = LoggerFactory.getLogger(TestLockService.class);

    @Autowired
    private JdbcLockRegistry jdbcLockRegistry;

    @Transactional
    public void testLock(String key) {
        logger.info("{} start with transaction {}", key,
                TransactionAspectSupport.currentTransactionStatus().hashCode());

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

        logger.info("{} end with transaction {}", key,
                TransactionAspectSupport.currentTransactionStatus().hashCode());
    }

    private void pretendToDoWork(String key) throws InterruptedException {
        Random random = new Random();
        int randomNumber = random.nextInt(3000);
        logger.info("{} pretend to work for {} before try to lock", key, randomNumber);
        Thread.sleep(randomNumber);
    }
}

package com.github.spring.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public abstract class BaseService {
    protected Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected abstract JdbcLockRegistry getJdbcLockRegistry();

    protected void lock(String key) {
        boolean isLocked = false;
        Lock lock = getJdbcLockRegistry().obtain(key);
        try {
            isLocked = lock.tryLock(10, TimeUnit.SECONDS);
            if (isLocked) {
                logger.info("{} lock ok", key);
            } else {
                logger.info("{} lock failed with no exception", key);
            }
        } catch (Throwable t) {
            logger.error("{} lock failed with exception", key, t);
        } finally {
            if (isLocked) {
                logger.info("{} unlock", key);
                lock.unlock();
            } else {
                logger.info("{} skip unlock since try lock failed", key);
            }
        }
    }
}

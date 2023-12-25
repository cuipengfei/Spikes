package com.github.spring.example.service;

import com.github.spring.example.CatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionStatus;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public abstract class BaseService {
    @Autowired
    private CatRepository catRepository;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract JdbcLockRegistry getJdbcLockRegistry();

    protected void doWorkInsideLock(String key) {
        logCurrentTransaction(key + " start of doWorkInsideLock");

        boolean isLocked = false;
        Lock lock = getJdbcLockRegistry().obtain(key);
        try {
            isLocked = lock.tryLock(10, TimeUnit.SECONDS);
            if (isLocked) {
                logCurrentTransaction(key + " get lock ok");
                catRepository.findById(UUID.randomUUID());
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

    protected Optional<Lock> tryGetLock(String key) {
        logCurrentTransaction(key + " start of tryGetLock");

        boolean isLocked = false;
        Lock lock = getJdbcLockRegistry().obtain(key);
        try {
            isLocked = lock.tryLock(10, TimeUnit.SECONDS);
            if (isLocked) {
                logCurrentTransaction(key + " get lock ok");
                return Optional.of(lock);
            } else {
                logger.info("{} lock failed with no exception", key);
            }
        } catch (Throwable t) {
            logger.error("{} lock failed with exception", key, t);
        }
        return Optional.empty();
    }

    protected void logCurrentTransaction(String location) {
        logger.info("{}, current transaction is {}",
                location,
                getCurrentTransactionName());
    }

    public static String getCurrentTransactionName() {
        String currentTrans = null;
        try {
            TransactionStatus transactionStatus = TransactionAspectSupport.currentTransactionStatus();
            if (transactionStatus instanceof DefaultTransactionStatus) {
                currentTrans = ((DefaultTransactionStatus) transactionStatus).getTransaction().toString();
            }
        } catch (NoTransactionException ignored) {
        }
        return currentTrans;
    }
}

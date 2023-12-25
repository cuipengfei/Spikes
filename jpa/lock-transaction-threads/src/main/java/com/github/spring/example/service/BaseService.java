package com.github.spring.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionStatus;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public abstract class BaseService {

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

package com.github.spring.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class Problem1Service extends BaseService {

    @Autowired
    @Qualifier("defaultJdbcLockRegistry")
    private LockRegistry jdbcLockRegistry;

    @Override
    protected LockRegistry getLockRegistry() {
        return jdbcLockRegistry;
    }

    @Transactional
    public void problem1() {
        logCurrentTransaction("start of problem1");
        Arrays.asList("a", "b", "c").parallelStream().forEach(key -> {
            // https://github.com/spring-projects/spring-integration/issues/3733
            logger.info("going to call lock method with key: {}", key);
            doWorkInsideLock(key);
        });
    }
}

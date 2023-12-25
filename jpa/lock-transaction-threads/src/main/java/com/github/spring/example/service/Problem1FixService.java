package com.github.spring.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class Problem1FixService extends BaseService {

    @Autowired
    @Qualifier("customLockRegistry")
    private JdbcLockRegistry jdbcLockRegistry;

    @Override
    protected JdbcLockRegistry getJdbcLockRegistry() {
        return jdbcLockRegistry;
    }

    @Transactional
    public void problem1Fix() {
        logCurrentTransaction("start of problem1Fix");
        Arrays.asList("a", "b", "c").parallelStream().forEach(key -> {
            logger.info("going to call lock method with key: {}", key);
            doWorkInsideLock(key);
        });
    }
}

package com.github.spring.example.service;

import com.github.spring.example.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;

@Service
public class Problem2Service extends BaseService {

    @Autowired
    @Qualifier("customLockRegistry")
    private JdbcLockRegistry jdbcLockRegistry;

    @Override
    protected JdbcLockRegistry getJdbcLockRegistry() {
        return jdbcLockRegistry;
    }

    @Autowired
    private CatRepository catRepository;

    @Transactional
    public void problem2() {
        logCurrentTransaction("start of problem2");

        logger.info("first run some sql with cat");
        catRepository.findById(UUID.randomUUID());

        Arrays.asList("a", "b", "c").parallelStream().forEach(key -> {
            logger.info("going to call lock method with key: {}", key);
            doWorkInsideLock(key);
        });
    }
}

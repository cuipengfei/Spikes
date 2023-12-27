package com.github.spring.example.service;

import com.github.spring.example.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;

@Service
public class Problem2Service extends BaseService {

    @Autowired
    @Qualifier("customLockRegistry")
    private LockRegistry jdbcLockRegistry;

    @Override
    protected LockRegistry getLockRegistry() {
        return jdbcLockRegistry;
    }

    @Autowired
    private CatRepository catRepository;

    @Transactional
    public void problem2() {
        logCurrentTransaction("start of problem2");

        logger.info("first run some sql with cat");
        catRepository.findById(UUID.randomUUID());
        //↑ this line will open the transaction that is bound to current thread

        Arrays.asList("a", "b", "c").parallelStream().forEach(key -> {
            logger.info("going to call lock method with key: {}", key);
            //↓ one of these 3 calls will run on the same thread of the outer scope
            //then jdbc lock will try to change transaction isolation level to serializable
            //and that will fail because transaction is already opened
            doWorkInsideLock(key);
        });
    }
}

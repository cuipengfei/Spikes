package com.github.spring.example.service;

import com.github.spring.example.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

@Service
public class Problem2BadFixService extends BaseService {

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
    public void problem2BadFix() throws ExecutionException, InterruptedException {
        logCurrentTransaction("start of problem2BadFix");

        logger.info("first run some sql with cat");
        catRepository.findById(UUID.randomUUID());

        ForkJoinPool customThreadPool = new ForkJoinPool(4);

        // this can make sure to not run in the current thread
        // so that there will not be any transaction level change errors and get lock will be ok
        // but the doWorkInsideLock method will not run inside transaction(the lock itself will, but not biz logic)
        // which is also the case for all previous services
        customThreadPool.submit(() -> {
            Arrays.asList("a", "b", "c").parallelStream().forEach(key -> {
                logger.info("going to call lock method with key: {}", key);
                doWorkInsideLock(key);
            });
        }).get();

        customThreadPool.shutdown();
    }
}

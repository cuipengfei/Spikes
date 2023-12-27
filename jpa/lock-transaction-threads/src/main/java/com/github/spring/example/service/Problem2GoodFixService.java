package com.github.spring.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

@Service
public class Problem2GoodFixService extends BaseService {

    @Autowired
    @Qualifier("customLockRegistry")
    private LockRegistry jdbcLockRegistry;

    @Override
    protected LockRegistry getLockRegistry() {
        return jdbcLockRegistry;
    }

    @Autowired
    private Problem2GoodFixDelegateService delegateService;

    // no trans annotation here
    public void problem2GoodFix() {
        logCurrentTransaction("start of problem2GoodFix"); // no trans

        Arrays.asList("a", "b", "c").parallelStream().forEach(key -> {
            delegateService.doBiz("do biz before lock"); // its own trans

            Optional<Lock> lock = tryGetLock(key); // in its own trans
            if (lock.isPresent()) {
                delegateService.doBiz("do biz after lock"); // in its own trans, no mixed in same method as lock
                logCurrentTransaction(key + " going to unlock"); // no trans
                lock.get().unlock();
            }
        });
    }

}

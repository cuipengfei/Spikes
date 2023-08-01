package com.github.spring.example;

import com.github.spring.example.service.RunWorkersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

// start docker before run test

// docker run -e POSTGRES_USER=localtest -e POSTGRES_PASSWORD=localtest -e POSTGRES_DB=orders -p 5432:5432 -d postgres:15.3

// docker run -p 6379:6379 -d redis:7.0.12 --requirepass "mypass"

@SpringBootTest
public class LockTest {
    Logger logger = LoggerFactory.getLogger(LockTest.class);

    @Autowired
    RunWorkersService runWorkersService;

    @Value("${lock.registry.name}")
    String registryName;

    @BeforeEach
    @AfterEach
    public void logMode() {
        logger.info("there are 3 modes: jdbc, redis, redisson. later 2 are spring's implementation vs redisson");
        logger.info("this test is in {} mode.", registryName);
    }

    /**
     * ↓  reproduce the issue of ttl has no effect when 2 workers running in 2 THREADS OF THE SAME JAVA PROCESS
     **/
    @Test
    public void runBothInTheSameJavaProcess() {
        runWorkersService.runBothWorkersInSameProcess();

        // run this one alone, this will reproduce the issue:
        // worker 1 gets the lock
        // then after ttl is expired
        // worker 2 still can not get the lock
        // later worker 2 gives up waiting for lock after timeout
        // the above seems only apply for spring integration, redisson works in a diff way
    }

    /**
     * ↓ when these 2 workers running in 2 SEPARATE JAVA PROCESSES, then it works as expected: ttl is respected
     **/
    @Test
    public void runWorker1InOneJavaProcess() {
        runWorkersService.runAsyncWorker("worker 1", 25).join();
        // run this one first in one java process
    }

    @Test
    public void runWorker2InAnotherJavaProcess() {
        runWorkersService.runAsyncWorker("worker 2", 25).join();
        // run this later in ANOTHER JAVA PROCESS
        // then we can see it works as expected
        // worker 2 can get the lock after ttl is expired
    }

    /**
     * ↓ when worker 1 and 2 are in the SAME JAVA PROCESS
     * try to let worker 2 get lock AFTER ttl even if worker 1 never release
     **/
    @Test
    public void runBothInSameJavaProcess_WithProactiveExpire() {
        runWorkersService.runBothInSameProcessWithProactiveExpire();
    }

    /**
     * ↓ when worker 1 and 2 are in the SAME JAVA PROCESS
     * worker 2 calls expireUnusedOlderThan before ttl of worker1
     * worker 2 should not be able to forcefully take lock from worker 1
     **/
    @Test
    public void runBothInSameJavaProcess_WithProactiveExpire_BeforeTTL() {
        runWorkersService.runBothInSameProcessWithProactiveExpireBeforeTTL();
    }

    /**
     * ↓ when worker 1 and 2 are in 2 DIFFERENT JAVA PROCESS
     * try to prove that the ProactiveExpire action should not forcefully take the lock from worker 1
     **/
    @Test
    public void runWorker2InAnotherJavaProcess_WithProactiveExpire() {
        runWorkersService.runWorker2WithProactiveExpire().join();
        // run worker 1 first, then run this later in ANOTHER JAVA PROCESS, but not so late that ttl has expired
        // the ProactiveExpire action should not grab lock from worker1 if ttl is not reached yet
        // worker 2 should only get the lock after ttl is expired
    }
}
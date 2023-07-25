package com.github.spring.example;

import com.github.spring.example.service.RunWorkersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// start docker before run test

// docker run -e POSTGRES_USER=localtest -e POSTGRES_PASSWORD=localtest -e POSTGRES_DB=orders -p 5432:5432 -d postgres:9.6.12

@SpringBootTest
public class LockTest {

    @Autowired
    RunWorkersService runWorkersService;

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

        // log output of this test will show the above ↑
    }


    /**
     * ↓ when these 2 workers running in 2 SEPARATE JAVA PROCESSES, then it works as expected: ttl is respected
     **/
    @Test
    public void runWorker1InOneJavaProcess() {
        runWorkersService.runWorker1().join();
        // run this one first in one java process
    }

    @Test
    public void runWorker2InAnotherJavaProcess() {
        runWorkersService.runWorker2().join();
        // run this later in ANOTHER JAVA PROCESS
        // then we can see it works as expected
        // worker 2 can get the lock after ttl is expired
    }

    /**
     * ↓ when worker 1 and 2 are in the SAME JAVA PROCESS
     * try to let worker 2 get lock even if worker 1 never release
     **/
    @Test
    public void testStuckThread() {
        runWorkersService.simulateStuckThread().join();
    }

    @Test
    public void runBothInSameJavaProcess_WithProactiveExpire() {
        runWorkersService.runBothInSameProcessWithProactiveExpire();
    }

    /**
     * ↓ when worker 1 and 2 are in the 2 DIFFERENT JAVA PROCESS
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
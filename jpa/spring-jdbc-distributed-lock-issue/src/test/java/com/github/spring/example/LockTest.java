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

    @Test
    public void runBothInTheSameJavaProcess() throws InterruptedException {
        runWorkersService.runBoth();

        // run this one alone, this will reproduce the issue:
        // worker 1 gets the lock
        // then after ttl is expired
        // worker 2 still can not get the lock
        // later worker 2 gives up waiting for lock after timeout

        // log output of this test will show the above ↑
    }

    /**
     * ↑ reproduce the issue of ttl has no effect when 2 workers running in 2 threads of the same java process
     * <p>
     * ↓ when these 2 workers running in 2 separate java processes, then it works as expected: ttl is respected
     **/

    @Test
    public void runWorker1() {
        runWorkersService.runWorker1().join();
        // run this one first in one java process
    }

    @Test
    public void runWorker2() {
        runWorkersService.runWorker2().join();
        // run this later in ANOTHER JAVA PROCESS
        // then we can see it works as expected
        // worker 2 can get the lock after ttl is expired
    }

}
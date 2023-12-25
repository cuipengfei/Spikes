package com.github.spring.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class TestPS {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("outside " + Thread.currentThread().getName());

        ForkJoinPool customThreadPool = new ForkJoinPool(4);
        customThreadPool.submit(() -> {
            System.out.println("middle " + Thread.currentThread().getName());

            IntStream.range(1, 100).parallel().forEach(num -> {
                System.out.println("inside " + Thread.currentThread().getName());
            });
        }).get();

        customThreadPool.shutdown();
    }
}

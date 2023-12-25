package com.github.spring.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class TestParallelStreamThreads {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("pool");
        withPool();

        System.out.println("----------");

        System.out.println("no pool");
        noPool();
    }

    private static void withPool() throws InterruptedException, ExecutionException {
        System.out.println("outside " + Thread.currentThread().getName());

        ForkJoinPool customThreadPool = new ForkJoinPool(4);
        customThreadPool.submit(() -> {
            System.out.println("middle " + Thread.currentThread().getName());

            IntStream.range(1, 10).parallel().forEach(num -> {
                System.out.println("inside " + Thread.currentThread().getName());
            });
        }).get();

        customThreadPool.shutdown();
    }

    private static void noPool() {
        System.out.println("outside " + Thread.currentThread().getName());

        IntStream.range(1, 10).parallel().forEach(num -> {
            System.out.println("inside " + Thread.currentThread().getName());
        });
    }
}

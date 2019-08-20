package com.monkeyzi.mboot.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ExecutorServiceDemo {

    public static void main(String[] args) {
        ExecutorService executorService=new ThreadPoolExecutor(5,
                10,
                60L,TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));
        IntStream.range(0,5).forEach(a->{
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName()+"在执行任务");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        executorService.shutdown();
    }
}

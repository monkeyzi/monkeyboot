package com.monkeyzi.mboot.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLockDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch=new CountDownLatch(50);
        final int[] counter={0};
        ReentrantLock lock=new ReentrantLock();
        IntStream.range(0,50).forEach(a->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                     lock.lock();
                     try {
                         System.out.println(Thread.currentThread().getName()+"----"+a+"----");
                         int a=counter[0];
                         counter[0]=a+1;
                     }finally {
                         lock.unlock();
                         latch.countDown();
                     }
                }
            }).start();
        });

        // 主线程阻塞，等待子线程计算完结果

        latch.await();
        System.out.println("最后计算结果为："+counter[0]);
    }
}

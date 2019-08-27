package com.monkeyzi.mboot.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch startGate=new CountDownLatch(1);
        final CountDownLatch endGate=new CountDownLatch(5);

        //线程池20        
        ExecutorService exce=Executors.newCachedThreadPool();
        for (int i=0;i<5;i++){
            final int  num=i;
            Thread thread=new Thread(() -> {
                try {
                    System.out.println(num+"号选手准备就绪，准备裁判员哨向！");
                    try {
                        startGate.await();
                        Thread.sleep((long) (Math.random()*10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(num+"号选手到达终点");
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    endGate.countDown();
                }

            });
            exce.execute(thread);
        }

        long start=System.nanoTime();
        System.out.println("裁判员哨声响起..");
        Thread.sleep(1000);
        startGate.countDown();
        endGate.await();
        long end=System.nanoTime();
        System.out.println("所有运动员到达终点,耗时:"+(end-start));
        exce.shutdown();
    }
}

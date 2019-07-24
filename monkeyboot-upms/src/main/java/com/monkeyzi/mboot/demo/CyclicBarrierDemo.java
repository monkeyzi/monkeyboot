package com.monkeyzi.mboot.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    public static void main(String[] args) {
        final  int  count=5;
        final CyclicBarrier cyclicBarrier=new CyclicBarrier(count, () -> System.out.println("人到齐了，开始吃饭了！"));
        for (int i=0;i<count;i++){
            new Thread(new myThread(i,cyclicBarrier)).start();
        }
    }

    public static class myThread implements Runnable{
        final  int count;
        final CyclicBarrier cyclicBarrier;
        public myThread(final  int count,final CyclicBarrier cyclicBarrier){
            this.count=count;
            this.cyclicBarrier=cyclicBarrier;
        }
        @Override
        public void run() {
            try {
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println(this.count + "到桌 !");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }
}

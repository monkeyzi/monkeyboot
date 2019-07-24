package com.monkeyzi.mboot.demo;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) {
        int count=8;
        Semaphore semaphore=new Semaphore(5);
        for (int i=0;i<count;i++){
           new Thread(new worker(i,semaphore)).start();
        }
    }

   static class worker implements Runnable{
       private int count;
       private Semaphore semaphore;

       public worker(int count,Semaphore semaphore){
           this.count=count;
           this.semaphore=semaphore;
       }
       @Override
       public void run() {
           try {
               semaphore.acquire();
               System.out.println("同学"+this.count+"占用一台电脑！");
               Thread.sleep(2000);
               semaphore.release();
               System.out.println("同学"+this.count+"下线回家吃饭了！");
           }catch (InterruptedException e){
               e.printStackTrace();
           }
       }
   }
}

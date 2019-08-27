package com.monkeyzi.mboot.demo;

/**
 * @author: 高yg
 * @date: 2019/8/4 22:57
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class DaemonThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(2_000L);
        System.out.println("Main thread finished");
    }
}

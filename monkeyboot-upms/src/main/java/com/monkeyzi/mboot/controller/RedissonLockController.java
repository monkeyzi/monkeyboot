package com.monkeyzi.mboot.controller;

import com.monkeyzi.mboot.redis.lock.IRedissonDistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author: 高yg
 * @date: 2019/7/21 21:30
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@RestController
public class RedissonLockController {

    @Autowired
    private IRedissonDistributedLock redissonDistributedLock;

    @RequestMapping("/test")
    public void redissonTest() {
        String key = "redisson_key";
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                try {
                    System.err.println("=============线程开启============"+Thread.currentThread().getName());
                    boolean isGetLock =  redissonDistributedLock.tryLock(key,TimeUnit.SECONDS,5L,10L);
                    if(isGetLock){
                        Thread.sleep(1000);
                        System.err.println("======获得锁后进行相应的操作======"+Thread.currentThread().getName());
                        redissonDistributedLock.unlock(key);
                        System.out.println("=================释放锁=="+Thread.currentThread().getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }

}

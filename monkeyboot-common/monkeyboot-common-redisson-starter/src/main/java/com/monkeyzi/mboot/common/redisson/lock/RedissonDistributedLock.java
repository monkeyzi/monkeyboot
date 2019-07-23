package com.monkeyzi.mboot.common.redisson.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author: 高yg
 * @date: 2019/7/21 21:03
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description: redisson分布式锁实现
 */
@Slf4j
@Component
public class RedissonDistributedLock implements IRedissonDistributedLock {

    @Autowired
    private RedissonClient redissonClient;

    public RedissonDistributedLock(RedissonClient client){
        super();
        this.redissonClient=client;
    }


    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;

    }

    @Override
    public RLock lock(String key, long timeout) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;

    }

    @Override
    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;

    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(RLock lock) {
         lock.unlock();
    }

    @Override
    public void unlock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }
}

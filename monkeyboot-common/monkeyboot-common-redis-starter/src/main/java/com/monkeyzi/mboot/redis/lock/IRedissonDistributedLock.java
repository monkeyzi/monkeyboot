package com.monkeyzi.mboot.redis.lock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @author: 高yg
 * @date: 2019/7/21 20:25
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface IRedissonDistributedLock {

    /**
     * redisson分布式锁获取锁--拿不到所就一直阻塞下去
     * @param lockKey
     * @return
     */
    RLock lock(String lockKey);

    /**
     * 指定枷锁时间
     * @param key
     * @param timeout
     * @return
     */
    RLock lock(String key, long timeout);

    /**
     * 获取锁
     * @param lockKey key
     * @param unit  时间单位
     * @param timeout 超时时间
     * @return
     */
    RLock lock(String lockKey, TimeUnit unit, long timeout);

    /**
     *   tryLock()，马上返回，拿到lock就返回true，不然返回false。
     *   带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false
     *   waitTime  时间内获取不到锁，就不再获取
     *   leaseTime 己获得锁后一直不解锁则leaseTime秒后自动解锁
     * @param lockKey
     * @param unit
     * @param waitTime
     * @param leaseTime
     * @return
     */
    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    /**
     * 释放锁
     * @param lock
     */
    void unlock(RLock lock);

    /**
     * redisson 释放锁
     * @param key
     */
    void unlock(String key);
}

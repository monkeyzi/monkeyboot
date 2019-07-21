package com.monkeyzi.mboot.common.core.lock;

/**
 * @author: 高yg
 * @date: 2019/7/21 20:25
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface IRedisDistributedLock {

    /**
     * redis分布式锁-获取锁
     * @param key
     * @return
     */
    boolean lock(String key);

    /**
     * redis释放锁
     * @param key
     */
    boolean   release(String key);
}

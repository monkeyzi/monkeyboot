package com.monkeyzi.mboot.common.core.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.monkeyzi.mboot.common.core.lock.IRedisDistributedLock;
import com.monkeyzi.mboot.common.core.result.R;

/**
 * @author: 高yg
 * @date: 2019/6/20 21:09
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface ISuperService<T> extends IService<T> {
    /**
     * 幂等新增记录
     * @param entity
     * @param lockKey
     * @param countWrapper
     * @return
     */
    R saveIdempotency(T entity, IRedisDistributedLock distributedLock,String lockKey, Wrapper<T> countWrapper,String msg);

    /**
     * 幂等新增记录
     * @param entity
     * @param lockKey
     * @return
     */
    R saveIdempotency(T entity,IRedisDistributedLock redisDistributedLock,String lockKey,Wrapper<T> countWrapper);

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return
     */
    R saveOrUpdateIdempotency(T entity, IRedisDistributedLock  lock, String lockKey, Wrapper<T> countWrapper, String msg);

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return
     */
    R saveOrUpdateIdempotency(T entity, IRedisDistributedLock lock, String lockKey, Wrapper<T> countWrapper);

}

package com.monkeyzi.mboot.common.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkeyzi.mboot.common.core.lock.IRedisDistributedLock;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.common.core.service.ISuperService;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author: 高yg
 * @date: 2019/6/20 21:17
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Slf4j
public class SuperServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements ISuperService<T> {

    @Override
    public R saveIdempotency(T entity, IRedisDistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg) {
        if (lock == null) {
            log.warn("lock为空");
            return R.errorMsg("lock为空");
        }
        if (StrUtil.isEmpty(lockKey)) {
            log.warn("lockKey为空");
            return R.errorMsg("lockKey为空");
        }
        try {
            //加锁
            boolean isLock = lock.lock(lockKey);
            if (isLock) {
                //判断记录是否已存在
                int count = super.count(countWrapper);
                if (count == 0) {
                  super.save(entity);
                  return R.ok();
                } else {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    return R.errorMsg(msg);
                }
            } else {
                return R.errorMsg("获取锁异常！");
            }
        } finally {
            lock.release(lockKey);
        }
    }

    @Override
    public R saveIdempotency(T entity, IRedisDistributedLock lock, String lockKey, Wrapper<T> countWrapper) {
        return saveIdempotency(entity,lock,lockKey,countWrapper,null);
    }

    @Override
    public R saveOrUpdateIdempotency(T entity, IRedisDistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    return this.saveIdempotency(entity, lock, lockKey, countWrapper, msg);
                } else {
                    this.updateById(entity);
                    return R.okMsg(msg);
                }
            } else {
                return R.errorMsg("@TableId注解不能为空！");
            }
        }
        return R.errorMsg(msg);
    }

    @Override
    public R saveOrUpdateIdempotency(T entity, IRedisDistributedLock lock, String lockKey, Wrapper<T> countWrapper) {
        return saveIdempotency(entity, lock, lockKey, countWrapper);
    }
}

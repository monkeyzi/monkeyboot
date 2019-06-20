package com.monkeyzi.mboot.common.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.common.core.service.ISuperService;

/**
 * @author: 高yg
 * @date: 2019/6/20 21:17
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class ISuperServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements ISuperService<T> {
    /**
     * 幂等新增
     * @param entity
     * @param lockKey
     * @param countWrapper
     * @return
     */
    public R saveIdempotency(T entity, String lockKey, Wrapper<T> countWrapper) {
        return null;
    }
}

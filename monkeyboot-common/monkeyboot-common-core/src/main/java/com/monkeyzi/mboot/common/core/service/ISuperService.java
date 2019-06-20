package com.monkeyzi.mboot.common.core.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
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
    R saveIdempotency(T entity,String lockKey, Wrapper<T> countWrapper);
}

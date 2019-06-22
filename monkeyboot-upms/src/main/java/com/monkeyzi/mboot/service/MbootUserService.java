package com.monkeyzi.mboot.service;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.service.ISuperService;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.protocal.req.UserPageReq;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootUserService extends ISuperService<MbootUser> {
    /**
     * 分页查询用户信息
     * @param req
     * @return
     */
    PageInfo listPageUserByCondition(UserPageReq req);
}

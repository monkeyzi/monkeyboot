package com.monkeyzi.mboot.mapper;

import com.monkeyzi.mboot.common.db.mapper.SuperMapper;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.protocal.req.UserPageReq;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:05
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootUserMapper extends SuperMapper<MbootUser> {

    List<MbootUser> selectUserByPageAndCondition(UserPageReq req);
}

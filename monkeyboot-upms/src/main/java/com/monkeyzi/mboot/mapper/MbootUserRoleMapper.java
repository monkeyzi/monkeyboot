package com.monkeyzi.mboot.mapper;

import com.monkeyzi.mboot.common.db.mapper.SuperMapper;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.entity.MbootUserRole;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:05
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootUserRoleMapper extends SuperMapper<MbootUserRole> {
    /**
     * 根据用户的Id删除用户角色信息表
     * @param userId
     * @return
     */
    boolean deleteUserRoleByUserId(Integer userId);
}

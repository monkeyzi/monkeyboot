package com.monkeyzi.mboot.service;

import com.monkeyzi.mboot.common.core.service.ISuperService;
import com.monkeyzi.mboot.entity.MbootRole;
import com.monkeyzi.mboot.entity.MbootRolePermission;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootRolePermissionService extends ISuperService<MbootRolePermission> {
    /**
     * 根据角色删除
     * @param roleId
     * @return
     */
    int deleteByRoleId(Integer  roleId);
}

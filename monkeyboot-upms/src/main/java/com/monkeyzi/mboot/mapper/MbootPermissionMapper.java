package com.monkeyzi.mboot.mapper;

import com.monkeyzi.mboot.common.db.mapper.SuperMapper;
import com.monkeyzi.mboot.entity.MbootDept;
import com.monkeyzi.mboot.entity.MbootPermission;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:05
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootPermissionMapper extends SuperMapper<MbootPermission> {
    /**
     * 根据角色Id查询权限信息
     * @param roleId
     * @return
     */
    List<MbootPermission> selectPermissionsByRoleId(Integer roleId);
}

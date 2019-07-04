package com.monkeyzi.mboot.service;

import com.monkeyzi.mboot.common.core.dto.PermissionTreeDto;
import com.monkeyzi.mboot.common.core.service.ISuperService;
import com.monkeyzi.mboot.entity.MbootPermission;
import com.monkeyzi.mboot.entity.MbootRole;

import java.util.Arrays;
import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootPermissionService extends ISuperService<MbootPermission> {
    /**
     * 查询角色的菜单权限
     * @param roleId
     * @return
     */
    List<MbootPermission> getPermissionsByRoleId(Integer roleId);

    /**
     * 查询当前用户的权限树
     * @return
     */
    List<PermissionTreeDto> getUserOwnPermissions();
}

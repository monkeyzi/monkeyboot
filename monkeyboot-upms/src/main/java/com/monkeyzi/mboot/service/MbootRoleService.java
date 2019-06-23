package com.monkeyzi.mboot.service;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.common.core.service.ISuperService;
import com.monkeyzi.mboot.entity.MbootRole;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.protocal.req.RolePageReq;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootRoleService extends ISuperService<MbootRole> {
    /**
     * 分页查询角色信息
     * @param req
     * @return
     */
    PageInfo listPageRoleByCondition(RolePageReq req);

    /**
     * 查询系统中所有的角色
     * @return
     */
    List<MbootRole> listAllRoles();

    /**
     * 校验角色标识
     * @param roleCode
     * @return
     */
    Integer checkRoleCode(String roleCode);


    /**
     * 觉得绑定菜单权限
     * @param roleId
     * @param permissions
     * @return
     */
    R roleBindPermission(String roleId, List<Integer> permissions);

    /**
     * 删除角色
     * @param id
     * @return
     */
    boolean delRoleById(Integer id);
}

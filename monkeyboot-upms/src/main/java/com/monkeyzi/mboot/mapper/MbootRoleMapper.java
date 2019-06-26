package com.monkeyzi.mboot.mapper;

import com.monkeyzi.mboot.common.db.mapper.SuperMapper;
import com.monkeyzi.mboot.entity.MbootRole;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.protocal.req.RolePageReq;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:05
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootRoleMapper extends SuperMapper<MbootRole> {
    /**
     * 分页条件查询角色
     * @param req
     * @return
     */
    List<MbootRole> selectPageRoleByCondition(RolePageReq req);

    /**
     * 查询用户所拥有的角色列表信息
     * @param userId
     * @return
     */
    List<MbootRole> selectRoleListByUserId(Integer userId);
}

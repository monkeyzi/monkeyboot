package com.monkeyzi.mboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.entity.MbootRole;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.enums.DelStatusEnum;
import com.monkeyzi.mboot.mapper.MbootRoleMapper;
import com.monkeyzi.mboot.mapper.MbootRolePermissionMapper;
import com.monkeyzi.mboot.mapper.MbootUserMapper;
import com.monkeyzi.mboot.protocal.req.RolePageReq;
import com.monkeyzi.mboot.service.MbootRolePermissionService;
import com.monkeyzi.mboot.service.MbootRoleService;
import com.monkeyzi.mboot.service.MbootUserService;
import com.monkeyzi.mboot.utils.util.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
public class MbootRoleServiceImpl extends SuperServiceImpl<MbootRoleMapper,MbootRole> implements MbootRoleService {

    @Autowired
    private MbootRoleMapper mbootRoleMapper;
    @Autowired
    private MbootRolePermissionService mbootRolePermissionService;


    @Override
    public PageInfo listPageRoleByCondition(RolePageReq req) {
        PageHelper.startPage(req.getPageNum(),req.getPageSize());
        if (PublicUtil.isNotEmpty(req.getEndTime())){
            req.setEndTime(req.getEndTime()+" 23:59:59");
        }
        List<MbootRole> roleList=mbootRoleMapper.selectPageRoleByCondition(req);
        PageInfo pageInfo=new PageInfo(roleList);
        return pageInfo;
    }

    @Override
    public List<MbootRole> listAllRoles() {
        return this.list(new QueryWrapper<MbootRole>().lambda().eq(MbootRole::getIsDel,DelStatusEnum.IS_NOT_DEL.getType()));
    }

    @Override
    public Integer checkRoleCode(String roleCode) {
        return this.count(new QueryWrapper<MbootRole>().lambda()
                .eq(MbootRole::getIsDel,DelStatusEnum.IS_NOT_DEL.getType())
        .eq(MbootRole::getRoleCode,roleCode));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R roleBindPermission(String roleId, List<Integer> permissions) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delRoleById(Integer id) {
        //删除角色菜单表
        mbootRolePermissionService.deleteByRoleId(id);
        //删除角色表
        return this.removeById(id);
    }

    @Override
    public List<MbootRole> getRoleListByUserId(Integer userId) {
        return mbootRoleMapper.selectRoleListByUserId(userId);
    }
}

package com.monkeyzi.mboot.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.entity.MbootPermission;
import com.monkeyzi.mboot.entity.MbootRole;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.enums.DelStatusEnum;
import com.monkeyzi.mboot.mapper.MbootUserMapper;
import com.monkeyzi.mboot.protocal.req.UserEditReq;
import com.monkeyzi.mboot.protocal.req.UserPageReq;
import com.monkeyzi.mboot.protocal.req.UserSaveReq;
import com.monkeyzi.mboot.protocal.resp.UserInfoVo;
import com.monkeyzi.mboot.security.entity.MbootLoginUser;
import com.monkeyzi.mboot.service.MbootPermissionService;
import com.monkeyzi.mboot.service.MbootRoleService;
import com.monkeyzi.mboot.service.MbootUserRoleService;
import com.monkeyzi.mboot.service.MbootUserService;
import com.monkeyzi.mboot.utils.util.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
public class MbootUserServiceImpl extends SuperServiceImpl<MbootUserMapper,MbootUser> implements MbootUserService {

    @Autowired
    private MbootUserMapper mbootUserMapper;
    @Autowired
    private MbootRoleService mbootRoleService;
    @Autowired
    private MbootPermissionService mbootPermissionService;


    @Override
    public PageInfo listPageUserByCondition(UserPageReq req) {
        PageHelper.startPage(req.getPageNum(),req.getPageSize());
        if (PublicUtil.isNotEmpty(req.getEndTime())){
            req.setEndTime(req.getEndTime()+" 23:59:59");
        }
        List<MbootUser> list=mbootUserMapper.selectUserByPageAndCondition(req);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public UserInfoVo getCurrentLoginUserInfo() {
        return null;
    }

    @Override
    public MbootUser getUserInfoVoById(Integer id) {
        return mbootUserMapper.selectUserInfoVoById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUserById(Integer id) {
        boolean flag=false;
        //删除用户关联的角色信息
        //逻辑删除用户表中数据
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(UserSaveReq req) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editUser(UserEditReq req) {
        return false;
    }

    @Override
    public Integer checkUserName(String username) {
        return this.count(new QueryWrapper<MbootUser>().lambda().eq(MbootUser::getUsername,username)
                .eq(MbootUser::getIsDel,DelStatusEnum.IS_NOT_DEL.getType()));
    }

    @Override
    public MbootLoginUser getUserByUserName(String username) {
        MbootUser mbootUser=this.getOne(new QueryWrapper<MbootUser>().lambda().eq(MbootUser::getUsername,username));
        return getLoginUser(mbootUser);
    }

    @Override
    public MbootLoginUser getUserByMobilePhone(String mobile) {
        MbootUser mbootUser=this.getOne(new QueryWrapper<MbootUser>().lambda().eq(MbootUser::getPhone,mobile));
        return getLoginUser(mbootUser);
    }

    @Override
    public MbootLoginUser getUserByWxOpenId(String wxOpenId) {
        MbootUser mbootUser=this.getOne(new QueryWrapper<MbootUser>().lambda().eq(MbootUser::getWxOpenId,wxOpenId));
        return getLoginUser(mbootUser);
    }


    private MbootLoginUser getLoginUser(MbootUser mbootUser){
        if (mbootUser!=null){
            MbootLoginUser loginUser=new MbootLoginUser();
            BeanUtils.copyProperties(mbootUser,loginUser);
            List<MbootRole> roleList=mbootRoleService.getRoleListByUserId(mbootUser.getId());
            //设置角色列表
            loginUser.setRoleList(roleList);
            //设置权限列表（permission.permission）
            Set<String> permissions = new HashSet<>();
            roleList.forEach(role -> {
                List<String> permissionList = mbootPermissionService.getPermissionsByRoleId(role.getId())
                        .stream()
                        .filter(menuVo -> StringUtils.isNotEmpty(menuVo.getPermission()))
                        .map(MbootPermission::getPermission)
                        .collect(Collectors.toList());
                permissions.addAll(permissionList);
            });
            loginUser.setPermissions(ArrayUtil.toArray(permissions, String.class));
            return loginUser;
        }
        return null;
    }

}

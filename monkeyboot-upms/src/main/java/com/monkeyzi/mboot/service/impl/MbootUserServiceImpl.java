package com.monkeyzi.mboot.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.constant.GlobalConstants;
import com.monkeyzi.mboot.common.core.exception.BusinessException;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.common.security.utils.SecurityUtils;
import com.monkeyzi.mboot.entity.MbootPermission;
import com.monkeyzi.mboot.entity.MbootRole;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.entity.MbootUserRole;
import com.monkeyzi.mboot.enums.DelStatusEnum;
import com.monkeyzi.mboot.mapper.MbootUserMapper;
import com.monkeyzi.mboot.protocal.req.BasicInfoEditReq;
import com.monkeyzi.mboot.protocal.req.UserEditReq;
import com.monkeyzi.mboot.protocal.req.UserPageReq;
import com.monkeyzi.mboot.protocal.req.UserSaveReq;
import com.monkeyzi.mboot.protocal.resp.UserInfoVo;
import com.monkeyzi.mboot.service.*;
import com.monkeyzi.mboot.utils.util.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private MbootUserRoleService mbootUserRoleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MbootFileService mbootFileService;


    @Override
    public PageInfo listPageUserByCondition(UserPageReq req) {
        PageHelper.startPage(req.getPageNum(),req.getPageSize());
        if (PublicUtil.isNotEmpty(req.getEndTime())){
            req.setEndTime(req.getEndTime()+GlobalConstants.DAY_LAST_TIME);
        }
        List<MbootUser> list=mbootUserMapper.selectUserByPageAndCondition(req);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public UserInfoVo getCurrentLoginUserInfo() {
        Integer userId=SecurityUtils.getLoginUser().getId();
        MbootUser mbootUser=this.getById(userId);
        if (mbootUser==null){
            throw new BusinessException("获取用户信息失败，用户不存在！");
        }
        UserInfoVo vo=new UserInfoVo();
        vo.setHeadImg(mbootUser.getHeadImg());
        vo.setNickName(mbootUser.getNickname());
        vo.setUserId(userId);
        vo.setUsername(mbootUser.getUsername());
        vo.setBrowser(mbootUser.getBrowser());
        vo.setOs(mbootUser.getOs());
        vo.setLastLoginIp(mbootUser.getLastLoginIp());
        vo.setLastLoginTime(mbootUser.getLastLoginTime());
        //角色信息
        List<Integer> roleIds = mbootRoleService.getRoleListByUserId(userId)
                .stream()
                .map(MbootRole::getId)
                .collect(Collectors.toList());
        vo.setRoleIds(ArrayUtil.toArray(roleIds, Integer.class));
       //设置权限标识列表
        Set<String> permissions = new HashSet<>();
        roleIds.forEach(roleId -> {
            List<String> permissionList = mbootPermissionService.getPermissionsByRoleId(roleId)
                    .stream()
                    .filter(menuVo -> StringUtils.isNotEmpty(menuVo.getPermission()))
                    .map(MbootPermission::getPermission)
                    .collect(Collectors.toList());
            permissions.addAll(permissionList);
        });
        vo.setPermissions(ArrayUtil.toArray(permissions, String.class));
        return vo;
    }

    @Override
    @Cacheable(value = "user_details", key = "#id  + '_user'")
    public MbootUser getUserInfoVoById(Integer id) {
        return mbootUserMapper.selectUserInfoVoById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user_details",key = "#id  + '_user'")
    public boolean deleteUserById(Integer id) {
        //删除用户关联的角色信息
        mbootUserRoleService.deleteUserRoleByUserId(id);
        //逻辑删除用户表中数据
        this.removeById(id);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(UserSaveReq req) {
        int count=mbootUserMapper.selectCount(new QueryWrapper<MbootUser>().lambda().eq(MbootUser::getUsername,req.getUsername()));
        if (count>0){
            throw new BusinessException("用户名已存在！");
        }
        MbootUser mbootUser=new MbootUser();
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.map(req,mbootUser);
        mbootUser.setIsDel(DelStatusEnum.IS_NOT_DEL.getType());
        mbootUser.setPassword(passwordEncoder.encode(mbootUser.getPassword()));
        mbootUser.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        this.save(mbootUser);
        //添加用户角色关系
        List<MbootUserRole> userRoleList = req.getRole()
                .stream().map(roleId -> {
                    MbootUserRole userRole = new MbootUserRole();
                    userRole.setUserId(mbootUser.getId());
                    userRole.setRoleId(roleId);
                    return userRole;
                }).collect(Collectors.toList());
        return mbootUserRoleService.saveBatch(userRoleList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user_details",key = "#req.getId()  + '_user'")
    public R editUser(UserEditReq req) {
        MbootUser user=this.getById(req.getId());
        if (user==null){
            return R.errorMsg("修改失败,用户不存在！");
        }
        if (StringUtils.isNotBlank(req.getPassword())){
            req.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.map(req,user);
        user.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        this.updateById(user);
        //修改用户角色关系-删除原来关系
        mbootUserRoleService.remove(Wrappers.<MbootUserRole>update().lambda()
                .eq(MbootUserRole::getUserId, req.getId()));
        //修改用户角色关系-新增新的关系
        req.getRole().forEach(roleId -> {
            MbootUserRole userRole = new MbootUserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(roleId);
            userRole.setCreateBy(SecurityUtils.getLoginUser().getUsername());
            userRole.insert();
        });
        return R.okMsg("修改成功！");
    }

    @Override
    public Integer checkUserName(String username) {
        return this.count(new QueryWrapper<MbootUser>().lambda().eq(MbootUser::getUsername,username)
                .eq(MbootUser::getIsDel,DelStatusEnum.IS_NOT_DEL.getType()));
    }

    @Override
    public MbootUser getUserByUserName(String username) {
        MbootUser mbootUser=this.getOne(new QueryWrapper<MbootUser>().lambda().eq(MbootUser::getUsername,username));
        return getLoginUser(mbootUser);
    }

    @Override
    public MbootUser getUserByMobilePhone(String mobile) {
        MbootUser mbootUser=this.getOne(new QueryWrapper<MbootUser>().lambda().eq(MbootUser::getPhone,mobile));
        return getLoginUser(mbootUser);
    }

    @Override
    public MbootUser getUserByWxOpenId(String wxOpenId) {
        MbootUser mbootUser=this.getOne(new QueryWrapper<MbootUser>().lambda().eq(MbootUser::getWxOpenId,wxOpenId));
        return getLoginUser(mbootUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user_details",key = "#userId + '_user'")
    public R editUserInfo(BasicInfoEditReq req,Integer userId) {
        MbootUser user=this.getById(userId);
        if (user==null){
            return R.errorMsg("修改失败,用户不存在！");
        }
        MbootUser mbootUser=new MbootUser();
        //用户图像
        if (req.getHeadImg()!=null){
            String result=mbootFileService.uploadFile(req.getHeadImg());
            if (StringUtils.isBlank(result)){
                return R.ok("修改失败,文件上传失败了！");
            }
            mbootUser.setHeadImg(result);
        }
        mbootUser.setPhone(req.getPhone());
        mbootUser.setEmail(req.getEmail());
        mbootUser.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        this.updateById(mbootUser);
        return R.errorMsg("修改成功！");
    }

    @Override
    public R editUserPwd(String password, String newPassword) {
        Integer userId=SecurityUtils.getLoginUser().getId();
        MbootUser user=this.getById(userId);
        if (user==null){
            throw new BusinessException("修改失败,用户不存在！");
        }
        if (!passwordEncoder.matches(password,user.getPassword())){
            log.error("密码修改失败,原密码不正确！");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        this.updateById(user);
        return R.okMsg("密码修改成功！");
    }


    private MbootUser getLoginUser(MbootUser mbootUser){
        if (mbootUser!=null){
            List<MbootRole> roleList=mbootRoleService.getRoleListByUserId(mbootUser.getId());
            //设置角色列表
            mbootUser.setRoleList(roleList);
            //设置权限列表
            Set<String> permissions = new HashSet<>();
            roleList.forEach(role -> {
                List<String> permissionList = mbootPermissionService.getPermissionsByRoleId(role.getId())
                        .stream()
                        .filter(menuVo -> StringUtils.isNotEmpty(menuVo.getPermission()))
                        .map(MbootPermission::getPermission)
                        .collect(Collectors.toList());
                permissions.addAll(permissionList);
            });
            mbootUser.setPermissions(ArrayUtil.toArray(permissions, String.class));
            return mbootUser;
        }
        return null;
    }

}

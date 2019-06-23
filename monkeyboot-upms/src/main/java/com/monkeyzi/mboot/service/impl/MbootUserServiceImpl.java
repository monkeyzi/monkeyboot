package com.monkeyzi.mboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.enums.DelStatusEnum;
import com.monkeyzi.mboot.mapper.MbootUserMapper;
import com.monkeyzi.mboot.protocal.req.UserEditReq;
import com.monkeyzi.mboot.protocal.req.UserPageReq;
import com.monkeyzi.mboot.protocal.req.UserSaveReq;
import com.monkeyzi.mboot.protocal.resp.UserInfoVo;
import com.monkeyzi.mboot.service.MbootUserService;
import com.monkeyzi.mboot.utils.util.PublicUtil;
import lombok.extern.slf4j.Slf4j;
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
public class MbootUserServiceImpl extends SuperServiceImpl<MbootUserMapper,MbootUser> implements MbootUserService {

    @Autowired
    private MbootUserMapper mbootUserMapper;


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




}

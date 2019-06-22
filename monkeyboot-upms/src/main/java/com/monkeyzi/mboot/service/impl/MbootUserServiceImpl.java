package com.monkeyzi.mboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.mapper.MbootUserMapper;
import com.monkeyzi.mboot.protocal.req.UserPageReq;
import com.monkeyzi.mboot.service.MbootUserService;
import com.monkeyzi.mboot.utils.util.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

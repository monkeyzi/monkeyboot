package com.monkeyzi.mboot.service.impl;

import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.entity.MbootDept;
import com.monkeyzi.mboot.entity.MbootPermission;
import com.monkeyzi.mboot.mapper.MbootDeptMapper;
import com.monkeyzi.mboot.mapper.MbootPermissionMapper;
import com.monkeyzi.mboot.service.MbootDeptService;
import com.monkeyzi.mboot.service.MbootPermissionService;
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
public class MbootPermissionServiceImpl extends SuperServiceImpl<MbootPermissionMapper,MbootPermission> implements MbootPermissionService {

    @Autowired
    private MbootPermissionMapper mbootPermissionMapper;


    @Override
    public List<MbootPermission> getPermissionsByRoleId(Integer roleId) {
        return mbootPermissionMapper.selectPermissionsByRoleId(roleId);
    }
}
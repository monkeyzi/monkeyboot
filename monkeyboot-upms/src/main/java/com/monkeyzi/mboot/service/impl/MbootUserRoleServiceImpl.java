package com.monkeyzi.mboot.service.impl;

import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.entity.MbootRole;
import com.monkeyzi.mboot.entity.MbootUserRole;
import com.monkeyzi.mboot.mapper.MbootRoleMapper;
import com.monkeyzi.mboot.mapper.MbootUserRoleMapper;
import com.monkeyzi.mboot.service.MbootRoleService;
import com.monkeyzi.mboot.service.MbootUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
public class MbootUserRoleServiceImpl extends SuperServiceImpl<MbootUserRoleMapper,MbootUserRole> implements MbootUserRoleService {
}

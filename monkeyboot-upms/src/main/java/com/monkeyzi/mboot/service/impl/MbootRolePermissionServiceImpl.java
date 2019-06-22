package com.monkeyzi.mboot.service.impl;

import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.entity.MbootPermission;
import com.monkeyzi.mboot.entity.MbootRolePermission;
import com.monkeyzi.mboot.mapper.MbootPermissionMapper;
import com.monkeyzi.mboot.mapper.MbootRolePermissionMapper;
import com.monkeyzi.mboot.service.MbootPermissionService;
import com.monkeyzi.mboot.service.MbootRolePermissionService;
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
public class MbootRolePermissionServiceImpl extends SuperServiceImpl<MbootRolePermissionMapper,MbootRolePermission> implements MbootRolePermissionService {
}

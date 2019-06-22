package com.monkeyzi.mboot.service.impl;

import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.entity.MbootDept;
import com.monkeyzi.mboot.entity.MbootDeptUser;
import com.monkeyzi.mboot.mapper.MbootDeptMapper;
import com.monkeyzi.mboot.mapper.MbootDeptUserMapper;
import com.monkeyzi.mboot.service.MbootDeptService;
import com.monkeyzi.mboot.service.MbootDeptUserService;
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
public class MbootDeptUserServiceImpl extends SuperServiceImpl<MbootDeptUserMapper,MbootDeptUser> implements MbootDeptUserService {
}

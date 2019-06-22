package com.monkeyzi.mboot.service.impl;

import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.entity.MbootErrorLog;
import com.monkeyzi.mboot.entity.MbootLog;
import com.monkeyzi.mboot.mapper.MbootErrorLogMapper;
import com.monkeyzi.mboot.mapper.MbootLogMapper;
import com.monkeyzi.mboot.service.MbootErrorLogService;
import com.monkeyzi.mboot.service.MbootLogService;
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
public class MbootErrorLogServiceImpl extends SuperServiceImpl<MbootErrorLogMapper,MbootErrorLog> implements MbootErrorLogService {
}

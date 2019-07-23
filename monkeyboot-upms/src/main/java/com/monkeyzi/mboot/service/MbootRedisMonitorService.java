package com.monkeyzi.mboot.service;

import java.util.Map;

/**
 * @author 高yg
 * @date 2019/7/23 15:57
 * @description  redis监控service
 **/
public interface MbootRedisMonitorService {
    /**
     * redis info信息
     * @return
     */
    Map<String,Object>  getRedisInfo();
}

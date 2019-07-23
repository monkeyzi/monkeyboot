package com.monkeyzi.mboot.controller;

import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.service.MbootRedisMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/monitor/redis")
@Api(description = "redis监控管理",value = "redis")
public class RedisMonitorController {

    @Autowired
    private MbootRedisMonitorService mbootRedisMonitorService;


    @ApiOperation(value = "redis监控信息")
    @GetMapping(value = "/info")
    public R  getRedisInfo(){
        Map<String,Object>  result=mbootRedisMonitorService.getRedisInfo();
        return R.ok("ok",result);
    }


}

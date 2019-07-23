package com.monkeyzi.mboot.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.monkeyzi.mboot.service.MbootRedisMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class MbootRedisMonitorServiceImpl implements MbootRedisMonitorService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> getRedisInfo() {
        //获取info信息
        Properties info= (Properties) redisTemplate.execute((RedisCallback) redisConnection -> redisConnection.info());
        Properties commandstats= (Properties) redisTemplate.execute((RedisCallback)
                redisConnection -> redisConnection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback) redisConnection -> redisConnection.dbSize());
        Map<String,Object> result=new HashMap<>();
        result.put("info", info);
        result.put("dbSize", dbSize);
        result.put("time", DateUtil.format(new Date(), DatePattern.NORM_TIME_PATTERN));
        List<Map<String, String>> pieList = new ArrayList<>();
        commandstats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(4);
            String property = commandstats.getProperty(key);
            data.put("name", StrUtil.removePrefix(key, "cmdstat_"));
            data.put("value", StrUtil.subBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
        result.put("commandStats", pieList);
        return result;
    }
}

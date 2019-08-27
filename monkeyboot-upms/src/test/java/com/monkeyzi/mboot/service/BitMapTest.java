package com.monkeyzi.mboot.service;

import com.monkeyzi.mboot.UpmsApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class BitMapTest extends UpmsApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void bitMap(){
        String key="online";
        Long userId=111L;
        Long userId3=333L;
        //设置用户的在线状态
        final Boolean online = redisTemplate.opsForValue().setBit(key, userId, true);
        final Boolean online3 = redisTemplate.opsForValue().setBit(key, userId3, true);
        //查询用户的在线状态
        final Boolean online1 = redisTemplate.opsForValue().getBit(key, userId);
        //统计在线用户人数
        final Long count = (Long) redisTemplate.execute((RedisCallback) redisConnection ->
                redisConnection.bitCount(key.getBytes()));
        System.out.println("在线人数"+count);
    }
}

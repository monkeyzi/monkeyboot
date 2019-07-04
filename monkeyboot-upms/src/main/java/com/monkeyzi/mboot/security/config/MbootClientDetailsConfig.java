package com.monkeyzi.mboot.security.config;

import com.monkeyzi.mboot.common.security.service.MbootClientDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;

@Configuration
public class MbootClientDetailsConfig {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 声明 ClientDetails实现
     */
    @Bean
    public MbootClientDetailService mbootClientDetailService() {
        MbootClientDetailService clientDetailsService = new MbootClientDetailService(dataSource);
        clientDetailsService.setRedisTemplate(redisTemplate);
        return clientDetailsService;
    }
}

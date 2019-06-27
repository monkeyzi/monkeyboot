package com.monkeyzi.mboot.redis.config;

import com.monkeyzi.mboot.redis.template.MbootRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置
 */
@Slf4j
@EnableCaching
@ConditionalOnClass(MbootRedisTemplate.class)
@EnableConfigurationProperties({RedisProperties.class})
public class RedisAutoConfiguration {



    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        log.info("redis初始化--------------ok");
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        RedisSerializer stringSerializer=new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer redisObjectSerializer=new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(redisObjectSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public MbootRedisTemplate mcloudRedisTemplate(RedisTemplate<String,Object> redisTemplate){
        return new MbootRedisTemplate(redisTemplate);
    }



}

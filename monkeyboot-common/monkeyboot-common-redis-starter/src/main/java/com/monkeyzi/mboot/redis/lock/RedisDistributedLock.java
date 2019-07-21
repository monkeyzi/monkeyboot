package com.monkeyzi.mboot.redis.lock;

import com.monkeyzi.mboot.common.core.lock.IRedisDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class RedisDistributedLock implements IRedisDistributedLock {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ThreadLocal<String> lockFlag = new ThreadLocal<>();

    private static final String UNLOCK_LUA;

    private static final long  TIME_OUT=10000;

    /*
     * 通过lua脚本释放锁,来达到释放锁的原子操作
     */
    static {
        UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] " +
                "then " +
                "    return redis.call(\"del\",KEYS[1]) " +
                "else " +
                "    return 0 " +
                "end ";
    }

    public RedisDistributedLock(RedisTemplate<String,Object> redisTemplate){
        super();
        this.redisTemplate=redisTemplate;
    }


    @Override
    public boolean lock(String key) {
        return this.setRedis(key,TIME_OUT);
    }

    @Override
    public boolean release(String key) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            final List<String> keys=new ArrayList<>();
            keys.add(key);
            final List<String> args=new ArrayList<>();
            args.add(lockFlag.get());

            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
            final Long result = redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
                Object nativeConnection=redisConnection.getNativeConnection();
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }
                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }
                return 0L;
            });
            return result!=null&&result>0;
        }catch (Exception e){
            log.error("redis 释放锁发生异常", e);
        }finally {
            lockFlag.remove();
        }
        return false;

    }

    private boolean setRedis(final  String key,final  long expire){
        try {
            String status = redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                String result=null;
                Object nativeConnection=redisConnection.getNativeConnection();
                String uuid=UUID.randomUUID().toString();
                lockFlag.set(uuid);
                if (nativeConnection instanceof JedisCluster){
                    result = ((JedisCluster) nativeConnection).set(key, uuid, "NX", "PX", expire);
                }else if (nativeConnection instanceof Jedis){
                    result = ((Jedis) nativeConnection).set(key, uuid, "NX", "PX", expire);
                }
                return result;
            });
            return !StringUtils.isEmpty(status);
        }catch (Exception e){
            log.error("set redisDistributeLock occured an exception", e);
            return false;
        }
    }
}

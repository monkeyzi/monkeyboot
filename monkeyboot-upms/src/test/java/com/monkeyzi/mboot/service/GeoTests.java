package com.monkeyzi.mboot.service;

import com.monkeyzi.mboot.UpmsApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/7/27 11:44
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class GeoTests extends UpmsApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;
    private static final String CITYKEY ="cityList";

    @Test
    public void  geoadd(){
        Long count=redisTemplate.opsForGeo().add(CITYKEY,new Point(116.405285,39.904989),"bj");
        System.out.println(count);
    }

    @Test
    public void geopos(){
        List<Point> points=redisTemplate.opsForGeo().position(CITYKEY,"bj","sh");
        System.out.println(points);
    }

    @Test
    public void geodist(){
        Distance distance=redisTemplate.opsForGeo().distance(CITYKEY,"bj","sh",RedisGeoCommands.DistanceUnit.KILOMETERS);
        System.out.println(distance);
    }

    @Test
    public void georadius(){
        Circle circle = new Circle(116,39, Metrics.KILOMETERS.getMultiplier());
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .sortAscending()
                .limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo()
                .radius(CITYKEY,circle,args);
        System.out.println(results);
    }


    @Test
    public void geoRadiusByMember(){
        Distance distance = new Distance(5,Metrics.KILOMETERS);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .sortAscending()
                .limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<String>>  results = redisTemplate.opsForGeo()
                .radius(CITYKEY,"bj",distance,args);
        System.out.println(results);
    }

    @Test
    public void geohash(){
        List<String> results = redisTemplate.opsForGeo()
                .hash(CITYKEY,"bj","sh");
        System.out.println(results);
    }
}

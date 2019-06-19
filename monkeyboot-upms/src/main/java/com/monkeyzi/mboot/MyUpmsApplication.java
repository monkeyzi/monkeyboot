package com.monkeyzi.mboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: 高yg
 * @date: 2019/6/19 21:30
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@SpringBootApplication
@MapperScan("com.monkeyzi.mboot.mapper")
public class MyUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyUpmsApplication.class);
    }
}

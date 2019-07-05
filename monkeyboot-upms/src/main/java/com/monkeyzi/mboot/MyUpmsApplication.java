package com.monkeyzi.mboot;

import com.monkeyzi.mboot.common.security.annotation.EnableMbootResourceServer;
import com.monkeyzi.mboot.swagger.annotation.EnableMbootSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author: 高yg
 * @date: 2019/6/19 21:30
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@SpringBootApplication
@EnableMbootResourceServer
@EnableMbootSwagger2
public class MyUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyUpmsApplication.class,args);
    }
}

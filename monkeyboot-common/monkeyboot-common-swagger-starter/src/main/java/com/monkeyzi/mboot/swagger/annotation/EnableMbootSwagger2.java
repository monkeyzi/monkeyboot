package com.monkeyzi.mboot.swagger.annotation;

import com.monkeyzi.mboot.swagger.config.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: 高yg
 * @date: 2019/02/25 21:34
 * @qq:854152531@qq.com
 * @description:接口文档自定义注解
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfiguration.class})
public @interface EnableMbootSwagger2 {
}


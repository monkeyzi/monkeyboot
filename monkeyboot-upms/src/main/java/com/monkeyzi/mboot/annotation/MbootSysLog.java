package com.monkeyzi.mboot.annotation;

import java.lang.annotation.*;

/**
 * @author: 高yg
 * @date: 2019/7/6 10:36
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MbootSysLog {
    String  value() default "";
}

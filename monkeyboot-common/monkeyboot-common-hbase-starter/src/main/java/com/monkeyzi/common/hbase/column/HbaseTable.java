package com.monkeyzi.common.hbase.column;

import java.lang.annotation.*;

/**
 * @ClassName: HbaseTable
 * @Description: 自定义注解，用于获取table
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface HbaseTable {

    // 表名
    String tableName() default "";
}

package com.monkeyzi.mboot.config;

import com.monkeyzi.mboot.common.db.config.DefaultMybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 高yg
 * @date: 2019/6/20 20:51
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Configuration
@MapperScan({"com.monkeyzi.mboot.mapper*"})
public class MybatisPlusConfig  extends DefaultMybatisPlusConfig {
}

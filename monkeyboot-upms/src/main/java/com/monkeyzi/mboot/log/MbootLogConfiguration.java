package com.monkeyzi.mboot.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
@Slf4j
public class MbootLogConfiguration {
    /**
     * 登录日志 bean
     * @return
     */
    @Bean
    public MbootLoginLogEventListener mbootLoginLogEventListener(){
        return new MbootLoginLogEventListener();
    }
    /**
     * 系统正常日志 bean
     * @return
     */
    @Bean
    public MbootCommonLogEventListener mbootCommonLogEventListener(){
        return new MbootCommonLogEventListener();
    }
    /**
     * 系统异常日志 bean
     * @return
     */
    @Bean
    public MbootErrorLogEventListener mbootErrorLogEventListener(){
        return new MbootErrorLogEventListener();
    }
}

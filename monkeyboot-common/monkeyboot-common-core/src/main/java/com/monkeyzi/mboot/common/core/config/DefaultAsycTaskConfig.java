package com.monkeyzi.mboot.common.core.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: 高yg
 * @date: 2019/6/20 22:02
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Getter
@Setter
@EnableAsync(proxyTargetClass = true)
@Slf4j
public class DefaultAsycTaskConfig {


    /**
     *  线程池维护线程的最小数量.
     */
    @Value("${mboot-task.corePoolSize:10}")
    private int corePoolSize;
    /**
     *  线程池维护线程的最大数量
     */
    @Value("${mboot-task.maxPoolSize:200}")
    private int maxPoolSize;
    /**
     *  队列最大长度
     */
    @Value("${mboot-task.queueCapacity:10}")
    private int queueCapacity;
    /**
     *  线程池前缀
     */
    @Value("${mboot-task.threadNamePrefix:MbootExecutor-}")
    private String threadNamePrefix;


    @Bean
    public TaskExecutor taskExecutor() {
        log.info("线程池初始化--------------ok");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        /*
           rejection-policy：当pool已经达到max size的时候，如何处理新任务
           CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}

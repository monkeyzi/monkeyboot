package com.monkeyzi.mboot.log;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@AllArgsConstructor
public class MbootLoginLogEventListener {

    /**
     * 1.系统日志表中插入日志 2：更新用户的登录信息(最后登录时间,ip等)
     * @param mbootLoginLogEvent
     */
    @Async
    @Order
    @EventListener(MbootLoginLogEvent.class)
    public void saveLog(MbootLoginLogEvent mbootLoginLogEvent){
        HttpServletRequest request= (HttpServletRequest) mbootLoginLogEvent.getSource();
        System.out.println(request.getRequestURI());
        System.out.println("-----------------");

    }
}

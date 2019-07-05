package com.monkeyzi.mboot.log;


import com.monkeyzi.mboot.entity.MbootErrorLog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@AllArgsConstructor
public class MbootErrorLogEventListener {

    /**
     * 记录系统的异常日志
     * @param mbootErrorLogEvent
     */
    @Async
    @Order
    @EventListener(MbootErrorLogEvent.class)
    public void saveLog(MbootErrorLogEvent mbootErrorLogEvent){
        MbootErrorLog errorLog= (MbootErrorLog) mbootErrorLogEvent.getSource();
    }
}

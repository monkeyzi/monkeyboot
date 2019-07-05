package com.monkeyzi.mboot.log;


import com.monkeyzi.mboot.entity.MbootLog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@AllArgsConstructor
public class MbootCommonLogEventListener {

    /**
     * 记录系统的操作日志
     * @param mbootCommonLogEvent
     */
    @Async
    @Order
    @EventListener(MbootCommonLogEvent.class)
    public void saveLog(MbootCommonLogEvent mbootCommonLogEvent){
        MbootLog mbootLog= (MbootLog) mbootCommonLogEvent.getSource();

    }
}

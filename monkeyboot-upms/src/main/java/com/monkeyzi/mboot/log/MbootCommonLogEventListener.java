package com.monkeyzi.mboot.log;


import com.monkeyzi.mboot.entity.MbootLog;
import com.monkeyzi.mboot.service.MbootLogService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class MbootCommonLogEventListener {

    @Autowired
    private MbootLogService mbootLogService;
    /**
     * 记录系统的操作日志
     * @param mbootCommonLogEvent
     */
    @Async
    @Order
    @EventListener(MbootCommonLogEvent.class)
    public void saveLog(MbootCommonLogEvent mbootCommonLogEvent){
        MbootLog mbootLog= (MbootLog) mbootCommonLogEvent.getSource();
        mbootLogService.save(mbootLog);

    }
}

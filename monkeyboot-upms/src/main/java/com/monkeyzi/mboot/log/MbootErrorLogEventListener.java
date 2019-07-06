package com.monkeyzi.mboot.log;


import com.monkeyzi.mboot.common.core.log.MbootErrorLogDto;
import com.monkeyzi.mboot.common.core.log.MbootErrorLogEvent;
import com.monkeyzi.mboot.common.security.utils.SecurityUtils;
import com.monkeyzi.mboot.entity.MbootErrorLog;
import com.monkeyzi.mboot.enums.DelStatusEnum;
import com.monkeyzi.mboot.service.MbootErrorLogService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 高yg
 * @date: 2019/7/6 12:09
 * @className:MbootErrorLogEventListener
 * @description:  错误日志
 */
@Slf4j
public class MbootErrorLogEventListener {


    @Autowired
    private MbootErrorLogService mbootErrorLogService;
    /**
     * 记录系统的异常日志
     * @param mbootErrorLogEvent
     */
    @Async
    @Order
    @EventListener(MbootErrorLogEvent.class)
    public void saveLog(MbootErrorLogEvent mbootErrorLogEvent){
        MbootErrorLogDto errorLog= (MbootErrorLogDto) mbootErrorLogEvent.getSource();
        Exception e=errorLog.getE();
        MbootErrorLog err=new MbootErrorLog();
        err.setServiceName(errorLog.getServiceName());
        err.setStatus(0);
        err.setIsDel(DelStatusEnum.IS_NOT_DEL.getType());
        err.setRequestUri(errorLog.getRequestUri());
        err.setMethod(errorLog.getMethod());
        if (null!=e){
            err.setExceptionSimpleName(e.getClass().getSimpleName());
            err.setExceptionMessage(e.getMessage());
            if (null!=e.getStackTrace()&&e.getStackTrace().length>0){
                err.setExceptionStack(getStackMsg(e));
            }
            err.setExceptionCause(e.getCause()==null?"":e.getCause().toString());
        }
        mbootErrorLogService.save(err);
    }

    /**
     * 异常堆栈转为String
     * @param e
     * @return
     */
    private static String getStackMsg(Exception e) {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackArray = e.getStackTrace();
        for (int i = 0; i < stackArray.length; i++) {
            StackTraceElement element = stackArray[i];
            sb.append(element.toString() + "\n");
        }
        return sb.toString();
    }
}

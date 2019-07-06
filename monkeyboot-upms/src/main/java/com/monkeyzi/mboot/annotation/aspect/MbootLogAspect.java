package com.monkeyzi.mboot.annotation.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.monkeyzi.mboot.annotation.MbootSysLog;
import com.monkeyzi.mboot.common.security.utils.SecurityUtils;
import com.monkeyzi.mboot.entity.MbootLog;
import com.monkeyzi.mboot.enums.DelStatusEnum;
import com.monkeyzi.mboot.log.MbootCommonLogEvent;
import com.monkeyzi.mboot.utils.util.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author: 高yg
 * @date: 2019/7/6 10:39
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Slf4j
@Aspect
@Component
public class MbootLogAspect {

    @Value("${spring.application.name}")
    private String serviceName;
    @Autowired
    private  ApplicationEventPublisher eventPublisher;
    private static final int MAX_SIZE=2000;

    @SneakyThrows
    @Around("@annotation(sysLog)")
    public Object around(ProceedingJoinPoint point, MbootSysLog sysLog){
        //类名
        String className = point.getTarget().getClass().getName();
        //方法名
        String methodName   = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}", className, methodName);
        // 发送异步日志事件
        HttpServletRequest request=WebUtils.getRequest();
        MbootLog mbootLog=new MbootLog();
        mbootLog.setLogName(sysLog.value());
        mbootLog.setClassName(className);
        mbootLog.setMethod(methodName);
        mbootLog.setIsDel(DelStatusEnum.IS_NOT_DEL.getType());
        mbootLog.setServiceName(serviceName);
        mbootLog.setLogType("操作日志");
        mbootLog.setRemoteIp(IpUtils.getIp(request));
        mbootLog.setUserAgent(UserAgentUtils.getBroswer(request));
        mbootLog.setRequestUri(request.getRequestURI());
        mbootLog.setOs(UserAgentUtils.getSystemVersion(request));
        mbootLog.setRequestType(request.getMethod());
        mbootLog.setParams(getParams(point,request));
        mbootLog.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        mbootLog.setCreateUserId(SecurityUtils.getLoginUser().getId());
        // TODO
        mbootLog.setTenantId(111);
        Long startTime = System.currentTimeMillis();
        LocalDateTime start=LocalDateTime.now();
        Object obj = point.proceed();
        Long endTime = System.currentTimeMillis();
        Long  executeTime=endTime-startTime;
        LocalDateTime end=LocalDateTime.now();
        mbootLog.setStartTime(start);
        mbootLog.setEndTime(end);
        mbootLog.setExecuteTime(executeTime.intValue());
        eventPublisher.publishEvent(new MbootCommonLogEvent(mbootLog));
        return obj;
    }


    private String  getParams(ProceedingJoinPoint point,HttpServletRequest request){
        String params="";
        try {
            Map<String,Object> logParams=ObjectUtil.getMap(request.getParameterMap());
            params=JacksonUtil.obj2String(CollUtil.isEmpty(logParams)?null:logParams);
            if (StrUtil.isNotBlank(params)){
                return params;
            }else {
                Object[] args = point.getArgs();
                if (args.length == 0) {
                    return params;
                }
                params = JacksonUtil.obj2String(args);
                if (params.length() > MAX_SIZE) {
                    params = params.substring(MAX_SIZE);
                }
            }

        }catch (Exception e){
            log.error("获取参数发生异常 method={},path={}",point.getSignature().getName(),request.getRequestURI());
        }
        return params;
    }

}

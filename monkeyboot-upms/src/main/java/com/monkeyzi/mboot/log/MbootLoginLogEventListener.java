package com.monkeyzi.mboot.log;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.monkeyzi.mboot.entity.MbootLoginLog;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.enums.DelStatusEnum;
import com.monkeyzi.mboot.service.MbootLoginLogService;
import com.monkeyzi.mboot.service.MbootUserService;
import com.monkeyzi.mboot.utils.util.IpUtils;
import com.monkeyzi.mboot.utils.util.ObjectUtil;
import com.monkeyzi.mboot.utils.util.UserAgentUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class MbootLoginLogEventListener {

    @Autowired
    private  MbootLoginLogService mbootLoginLogService;
    @Autowired
    private MbootUserService mbootUserService;

    @Value("${spring.application.name}")
    private String serviceName;

    /**
     * 1.系统登录日志表中插入日志 2：更新用户的登录信息(最后登录时间,ip等)
     * @param mbootLoginLogEvent
     */
    @Async
    @Order
    @EventListener(MbootLoginLogEvent.class)
    public void saveLog(MbootLoginLogEvent mbootLoginLogEvent){
        HttpServletRequest request= (HttpServletRequest) mbootLoginLogEvent.getSource();
        //请求路径
        String requestUri=request.getRequestURI();
        //请求方法类型
        String requestType=request.getMethod();
        //方法参数
        String params=ObjectUtil.mapToString(request.getParameterMap());
        //ip
        String ip=IpUtils.getIp(request);
        //操作系统
        String os=UserAgentUtils.getSystemVersion(request);
        //浏览器
        String userAgent=UserAgentUtils.getBroswer(request);
        //日志名称
        String logName="登录系统";
        //服务名
        String service=serviceName;
        //创建人
        String createBy= (String) ObjectUtil.getMap(request.getParameterMap()).get("username");
        MbootLoginLog loginLog=new MbootLoginLog();
        loginLog.setCreateBy(createBy);
        loginLog.setIsDel(DelStatusEnum.IS_NOT_DEL.getType());
        loginLog.setLogName(logName);
        loginLog.setTenantId(111);
        loginLog.setOs(os);
        loginLog.setRemoteIp(ip);
        loginLog.setParams(params);
        loginLog.setServiceName(service);
        loginLog.setRequestUri(requestUri);
        loginLog.setRequestType(requestType);
        loginLog.setUserAgent(userAgent);
        MbootUser user=mbootUserService.getOne(new QueryWrapper<MbootUser>().lambda().eq(MbootUser::getUsername,createBy));
        if (user!=null){
            loginLog.setCreateUserId(user.getId());
        }
        //执行保存
        mbootLoginLogService.save(loginLog);
        //更新用户的登录信息
        user.setLastLoginIp(ip);
        user.setLastLoginTime(LocalDateTime.now());
        user.setOs(os);
        user.setBrowser(userAgent);
        mbootUserService.update(user,new UpdateWrapper<MbootUser>().lambda().eq(MbootUser::getUsername,createBy));
    }
}

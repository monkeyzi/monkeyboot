package com.monkeyzi.mboot.controller;

import com.monkeyzi.mboot.common.core.result.R;
import eu.bitwalker.useragentutils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: 高yg
 * @date: 2019/6/19 22:06
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@RestController
@Slf4j
public class MbootUserController {

    @Resource
    private TaskExecutor taskExecutor;

    @GetMapping(value = "/index")
    public R index(HttpServletRequest request){
        //获取浏览器信息
        Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
        final OperatingSystem operatingSystem = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getOperatingSystem();
        //获取浏览器版本号
        Version version = browser.getVersion(request.getHeader("User-Agent"));
        final BrowserType browserType = browser.getBrowserType();
        System.out.println(browser.getName()+"version="+version+"type="+browserType.getName()+"system="+operatingSystem.getName());
        return R.ok("ok",null);
    }

    @GetMapping(value = "/holder")
    public R holder(){
        return R.okMsg("查询成功");
    }
}

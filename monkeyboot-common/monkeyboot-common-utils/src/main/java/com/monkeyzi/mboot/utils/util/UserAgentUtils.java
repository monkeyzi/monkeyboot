package com.monkeyzi.mboot.utils.util;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取浏览器信息和操作系统的信息
 */
@Slf4j
public class UserAgentUtils {
    /**
     * 获取浏览器的名称
     * @param request
     * @return
     */
    public static String  getBroswer(HttpServletRequest request){
        Browser browser =browser(request);
        if (browser==null){
            log.warn("获取浏览器信息失败了");
            return null;
        }
        return browser.getName();
    }
    /**
     * 获取浏览器的版本
     * @param request
     * @return
     */
    public static String  getBroswerVersion(HttpServletRequest request){
        Browser browser =browser(request);
        if (browser==null){
            log.warn("获取浏览器信息失败了");
            return null;
        }
        return browser.getVersion(request.getHeader("User-Agent")).getVersion();
    }
    /**
     * 获取操作系统名
     * @param request
     * @return
     */
    public static String  getSystemVersion(HttpServletRequest request){
        OperatingSystem operatingSystem =system(request);
        if (operatingSystem==null){
            log.warn("获取操作系统信息失败了");
            return null;
        }
        return operatingSystem.getName();
    }

    /**
     * 获取浏览器
     * @param request
     * @return
     */
    private static Browser  browser(HttpServletRequest request){
        Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
        return browser;
    }

    /**
     * 获取操作系统
     * @param request
     * @return
     */
    private static OperatingSystem system(HttpServletRequest request){
        OperatingSystem operatingSystem = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getOperatingSystem();
        return operatingSystem;
    }
}

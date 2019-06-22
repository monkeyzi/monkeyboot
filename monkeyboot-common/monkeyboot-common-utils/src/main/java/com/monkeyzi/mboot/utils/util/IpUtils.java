package com.monkeyzi.mboot.utils.util;

import com.monkeyzi.mboot.utils.constant.IpConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: 高yg
 * @date: 2019/6/20 22:32
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description: 获取ip工具类
 */
@Slf4j
public class IpUtils {

    /**
     * 获得用户远程ip
     * @param request the request
     * @return the string
     */
    public static String getIp(HttpServletRequest request) {
        String ipAddress = request.getHeader(IpConstant.X_REAL_IP);
        if (StringUtils.isEmpty(ipAddress) || IpConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(IpConstant.X_FORWARDED_FOR);
        }
        if (StringUtils.isEmpty(ipAddress) || IpConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(IpConstant.PROXY_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ipAddress) || IpConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(IpConstant.WL_PROXY_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ipAddress) || IpConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(IpConstant.HTTP_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ipAddress) || IpConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(IpConstant.HTTP_X_FORWARDED_FOR);
        }
        if (StringUtils.isEmpty(ipAddress) || IpConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if (StringUtils.isEmpty(ipAddress) || IpConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (IpConstant.LOCALHOST_IP.equals(ipAddress) || IpConstant.LOCALHOST_IP_16.equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("获取IP地址, 出现异常={}", e.getMessage(), e);
                }
                assert inet != null;
                ipAddress = inet.getHostAddress();
            }
            log.info("获取IP地址 ipAddress={}", ipAddress);
        }
        // 对于通过多个代理的情况, 第一个IP为客户端真实IP,多个IP按照','分割 //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > IpConstant.MAX_IP_LENGTH) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}



package com.monkeyzi.mboot.common.security.utils;

import com.monkeyzi.mboot.common.security.entity.MbootLoginUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 登录用户工具类
 */
//该注解将普通类转换为工具类
@UtilityClass
public class SecurityUtils {
    /**
     * 获取Authentication
     * @return
     */
    public Authentication  getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public MbootLoginUser getLoginUser(Authentication authentication){
        Object principal=authentication.getPrincipal();
        if (principal instanceof MbootLoginUser){
            return (MbootLoginUser) principal;
        }
        return null;
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public MbootLoginUser getLoginUser(){
        Authentication authentication=getAuthentication();
        return getLoginUser(authentication);
    }



}

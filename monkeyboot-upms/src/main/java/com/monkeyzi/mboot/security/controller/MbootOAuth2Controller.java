package com.monkeyzi.mboot.security.controller;

import cn.hutool.core.util.StrUtil;
import com.monkeyzi.mboot.common.core.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * oauth2登录相关操作
 */
@Slf4j
@RestController
@RequestMapping(value = "/token")
public class MbootOAuth2Controller {

    @Autowired
    private TokenStore tokenStore;

    @RequestMapping(value = "/login")
    @ResponseBody
    public R login(HttpServletResponse response){
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        response.setStatus(401);
        return R.error(401,"请登录认证！");
    }


    @DeleteMapping("/logout")
    public R logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        if (StrUtil.isBlank(authHeader)) {
            return R.errorMsg("退出失败，token 为空");
        }
        String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
            return R.errorMsg("退出失败，token 无效");
        }
        tokenStore.removeAccessToken(accessToken);
        return R.okMsg("退出成功");
    }

}

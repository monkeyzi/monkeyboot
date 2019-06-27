package com.monkeyzi.mboot.security.controller;

import cn.hutool.core.map.MapUtil;
import com.monkeyzi.mboot.common.core.constant.SecurityConstants;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.security.service.MbootClientDetailService;
import com.monkeyzi.mboot.security.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * oauth2登录相关操作
 */
@Slf4j
@RestController
public class MbootOAuth2Controller {

    @Autowired
    private MbootClientDetailService mbootClientDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    /**
     * 用户名密码登录
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = SecurityConstants.PASSWORD_LOGIN_PRO_URL)
    public R  loginByUsername(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpServletRequest  request,
                              HttpServletResponse response){
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username,password);
        OAuth2AccessToken accessToken=handlerAccessToken(request,response,token);
        return R.ok("登录成功",accessToken);
    }


    /**
     * 处理access_token
     * @param request
     * @param response
     * @param token
     * @return
     */
    private OAuth2AccessToken handlerAccessToken(HttpServletRequest request,HttpServletResponse response,
                                                 AbstractAuthenticationToken token){
            final String[] clients=AuthUtils.extractAndDecodeHeader(request);
            String clientId=clients[0];
            String clientSecret=clients[1];
            ClientDetails clientDetails = mbootClientDetailService.loadClientByClientId(clientId);
            if (clientDetails == null) {
                throw new UnapprovedClientAuthenticationException("clientId对应的信息不存在");
            } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
                throw new UnapprovedClientAuthenticationException("clientSecret不匹配");
            }
            TokenRequest tokenRequest = new TokenRequest(MapUtil.newHashMap(), clientId,
                    clientDetails.getScope(), "customer");
            //校验scope
            new DefaultOAuth2RequestValidator().validateScope(tokenRequest, clientDetails);
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
            OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
            oAuth2Authentication.setAuthenticated(true);
            return oAuth2AccessToken;
    }

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("mboot"));
    }

}

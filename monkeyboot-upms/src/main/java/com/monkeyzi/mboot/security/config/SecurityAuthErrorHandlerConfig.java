package com.monkeyzi.mboot.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkeyzi.mboot.utils.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
 * 认证处理
 */
@Configuration
@Slf4j
public class SecurityAuthErrorHandlerConfig {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 认证失败处理
     * @return
     */
    @Bean
    public AuthenticationFailureHandler  authenticationFailureHandler(){
        return (request, response, e) -> {
             String msg;
             if (e instanceof BadCredentialsException){
                 msg="用户名或者密码错误！";
             }else{
                 msg=e.getMessage();
             }
            ResponseUtil.responseJson(objectMapper,response,msg,HttpStatus.UNAUTHORIZED.value(),Boolean.FALSE);
        };
    }

    /**
     * 重写默认异常处理
     * @return
     */
    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator(){
        return new DefaultWebResponseExceptionTranslator(){
            public static final String BAD_MSG = "坏的凭证";
            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                OAuth2Exception oAuth2Exception;
                if (e.getMessage() != null && e.getMessage().equals(BAD_MSG)) {
                    oAuth2Exception = new InvalidGrantException("用户名或密码错误", e);
                } else if (e instanceof InternalAuthenticationServiceException) {
                    oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
                } else if (e instanceof RedirectMismatchException) {
                    oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
                } else if (e instanceof InvalidScopeException) {
                    oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
                } else if (e instanceof AccessDeniedException){
                    oAuth2Exception=new InvalidGrantException(e.getMessage(),e);
                }else {
                    oAuth2Exception = new UnsupportedResponseTypeException("接口出小差了！", e);
                }
                ResponseEntity<OAuth2Exception> response = super.translate(oAuth2Exception);
                ResponseEntity.status(oAuth2Exception.getHttpErrorCode());
                //处理请求头
                int status = oAuth2Exception.getHttpErrorCode();
                response.getHeaders().add(HttpHeaders.CACHE_CONTROL, "no-store");
                response.getHeaders().add(HttpHeaders.PRAGMA, "no-cache");
                if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
                    response.getHeaders().add(HttpHeaders.WWW_AUTHENTICATE, String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, oAuth2Exception.getSummary()));
                }
                //响应body体
                response.getBody().addAdditionalInformation("code", oAuth2Exception.getHttpErrorCode()+"");
                response.getBody().addAdditionalInformation("msg", oAuth2Exception.getMessage());
                response.getBody().addAdditionalInformation("data","");
                return response;
            }
        };
    }

    /**
     * 认证成功处理
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new SavedRequestAwareAuthenticationSuccessHandler(){
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }

}

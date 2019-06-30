package com.monkeyzi.mboot.security.mobile;

import com.monkeyzi.mboot.common.core.constant.SecurityConstants;
import com.monkeyzi.mboot.security.config.resource.MbootAuthExceptionEntryPoint;
import com.monkeyzi.mboot.security.service.MbootUserDetailService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

/**
 * 手机号密码登录配置类
 */
@Getter
@Setter
@Component
@Slf4j
public class MobileAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private MbootUserDetailService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MbootAuthExceptionEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationEventPublisher defaultAuthenticationEventPublisher;
    private AuthenticationSuccessHandler mobileLoginSuccessHandler;
    @Override
    public void configure(HttpSecurity http) {
        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();
        mobileAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(mobileLoginSuccessHandler);
        mobileAuthenticationFilter.setEventPublisher(defaultAuthenticationEventPublisher);
        mobileAuthenticationFilter.setAuthenticationEntryPoint(authenticationEntryPoint);

        MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider();
        mobileAuthenticationProvider.setMbootUserDetailService(userDetailsService);
        mobileAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        http.authenticationProvider(mobileAuthenticationProvider)
                .addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}

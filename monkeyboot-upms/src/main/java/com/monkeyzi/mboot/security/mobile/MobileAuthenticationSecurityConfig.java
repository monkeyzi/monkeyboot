package com.monkeyzi.mboot.security.mobile;

import com.monkeyzi.mboot.security.service.MbootUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * 手机号密码登录配置类
 */
@Component
public class MobileAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private MbootUserDetailService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void configure(HttpSecurity http) {
        MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setMbootUserDetailService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        http.authenticationProvider(provider);
    }

}

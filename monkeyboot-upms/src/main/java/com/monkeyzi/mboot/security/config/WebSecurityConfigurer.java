package com.monkeyzi.mboot.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkeyzi.mboot.common.core.constant.SecurityConstants;
import com.monkeyzi.mboot.common.security.service.MbootClientDetailService;
import com.monkeyzi.mboot.config.MbootPasswordConfig;
import com.monkeyzi.mboot.security.mobile.MobileAuthenticationSecurityConfig;
import com.monkeyzi.mboot.security.mobile.MobileLoginSuccessHandler;
import com.monkeyzi.mboot.security.service.MbootUserDetailService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author 高yg
 * @date 2019/6/27 11:27
 * @description 认证相关配置
 **/
@Primary
@Order(90)
@Configuration
@Import(MbootPasswordConfig.class)
public class WebSecurityConfigurer   extends WebSecurityConfigurerAdapter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MbootUserDetailService userDetailsService;
    @Autowired
    private MbootClientDetailService mbootClientDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Lazy
    @Autowired
    private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
    /***
     * 认证管理对象
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 手机号登录配置
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler mobileLoginSuccessHandler() {
        return MobileLoginSuccessHandler.builder()
                .clientDetailsService(mbootClientDetailService)
                .defaultAuthorizationServerTokenServices(defaultAuthorizationServerTokenServices)
                .objectMapper(objectMapper)
                .passwordEncoder(passwordEncoder).build();
    }

    @Bean
    public MobileAuthenticationSecurityConfig mobileSecurityConfigurer() {
        MobileAuthenticationSecurityConfig mobileSecurityConfigurer = new MobileAuthenticationSecurityConfig();
        mobileSecurityConfigurer.setMobileLoginSuccessHandler(mobileLoginSuccessHandler());
        mobileSecurityConfigurer.setUserDetailsService(userDetailsService);
        return mobileSecurityConfigurer;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.LOGIN_PAGE)
                .loginProcessingUrl(SecurityConstants.OAUTH_LOGIN_PRO_URL)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/token/**",
                        "/actuator/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .apply(mobileSecurityConfigurer())
                .and()
                // 解决不允许显示在iframe的问题
                .headers().frameOptions().disable().cacheControl();
    }
}

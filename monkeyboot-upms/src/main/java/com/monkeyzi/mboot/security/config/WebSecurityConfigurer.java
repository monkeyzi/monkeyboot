package com.monkeyzi.mboot.security.config;

import com.monkeyzi.mboot.common.core.constant.SecurityConstants;
import com.monkeyzi.mboot.config.MbootPasswordConfig;
import com.monkeyzi.mboot.security.config.resource.MbootAuthExceptionEntryPoint;
import com.monkeyzi.mboot.security.mobile.MobileAuthenticationSecurityConfig;
import com.monkeyzi.mboot.security.mobile.MobileLoginSuccessHandler;
import com.monkeyzi.mboot.security.properties.SecurityProperties;
import com.monkeyzi.mboot.security.service.MbootUserDetailService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

/**
 * @author 高yg
 * @date 2019/6/27 11:27
 * @description 认证相关配置
 **/
@Configuration
@Primary
@Order(90)
@EnableConfigurationProperties(SecurityProperties.class)
@Import(MbootPasswordConfig.class)
public class WebSecurityConfigurer   extends WebSecurityConfigurerAdapter {


    @Autowired
    private MbootAuthExceptionEntryPoint authenticationEntryPoint;
    @Autowired
    private MbootUserDetailService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityProperties securityProperties;


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
     * 全局用户对象
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationSuccessHandler mobileLoginSuccessHandler() {
        return MobileLoginSuccessHandler.builder().build();
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
        http.authorizeRequests()
                .antMatchers(securityProperties.getIgnore().getUrls())
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(SecurityConstants.LOGIN_PAGE)
                .loginProcessingUrl(SecurityConstants.OAUTH_LOGIN_PRO_URL)
                .and()
                .logout()
                .logoutUrl(SecurityConstants.LOGOUT_URL)
                .logoutSuccessUrl(SecurityConstants.LOGIN_PAGE)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                //.addLogoutHandler(oauthLogoutHandler)
                .and()
                .csrf().disable()
                .apply(mobileSecurityConfigurer())
                .and()
                // 解决不允许显示在iframe的问题
                .headers().frameOptions().disable().cacheControl();

        // 基于密码 等模式可以无session,不支持授权码模式
        if (authenticationEntryPoint != null) {
            http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        } else {
            // 授权码模式单独处理，需要session的支持，此模式可以支持所有oauth2的认证
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        }
    }
}

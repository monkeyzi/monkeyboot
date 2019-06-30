package com.monkeyzi.mboot.security.config.resource;

import com.monkeyzi.mboot.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
//资源服务器注解加上之后，实现的手机号登录以及其他登录方式会404
//@EnableResourceServer
@EnableConfigurationProperties(SecurityProperties.class)
public class ResourceServerConfiguration extends ResourceServerConfigAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl = setHttp(http)
                .authorizeRequests()
                .antMatchers(securityProperties.getIgnore().getUrls()).permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest();
        setAuthenticate(authorizedUrl);
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        http.headers()
                .frameOptions()
                .disable()
                .and()
                .csrf().disable();
    }

}

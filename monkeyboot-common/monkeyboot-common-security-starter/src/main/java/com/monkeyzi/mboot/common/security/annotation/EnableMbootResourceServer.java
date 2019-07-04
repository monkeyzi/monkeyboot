package com.monkeyzi.mboot.common.security.annotation;

import com.monkeyzi.mboot.common.security.config.MbootResourceServerAutoConfiguration;
import com.monkeyzi.mboot.common.security.config.MbootResourceServerConfigurerAdapter;
import com.monkeyzi.mboot.common.security.config.MbootSecurityBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * 资源服务注解
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({MbootResourceServerAutoConfiguration.class,MbootResourceServerConfigurerAdapter.class})
public @interface EnableMbootResourceServer {

}

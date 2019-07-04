package com.monkeyzi.mboot.common.security.config;

import com.monkeyzi.mboot.common.security.properties.PermitAllUrlProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

@Slf4j
public class MbootResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {
	@Autowired
	protected MbootAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;
	@Autowired
	private PermitAllUrlProperties permitAllUrlProperties;
	@Autowired
	private TokenStore tokenStore;


	/**
	 * 默认的配置，对外暴露
	 *
	 * @param httpSecurity
	 */
	@Override
	@SneakyThrows
	public void configure(HttpSecurity httpSecurity) {
		//允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
		httpSecurity.headers().frameOptions().disable();
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>
			.ExpressionInterceptUrlRegistry registry = httpSecurity
			.authorizeRequests();
		permitAllUrlProperties.getIgnoreUrls()
			.forEach(url -> registry.antMatchers(url).permitAll());
		registry.anyRequest().authenticated()
			.and().csrf().disable();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.authenticationEntryPoint(resourceAuthExceptionEntryPoint)
				.tokenStore(tokenStore);

	}
}

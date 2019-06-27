package com.monkeyzi.mboot.security.config;

import com.monkeyzi.mboot.common.core.constant.SecurityConstants;
import com.monkeyzi.mboot.common.core.utils.TenantContextHolder;
import com.monkeyzi.mboot.security.entity.MbootLoginUser;
import com.monkeyzi.mboot.security.service.MbootClientDetailService;
import com.monkeyzi.mboot.security.service.MbootUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务配置
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
@AutoConfigureAfter(AuthorizationServerEndpointsConfigurer.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final MbootUserDetailService mbootUserDetailService;

    private final WebResponseExceptionTranslator webResponseExceptionTranslator;

    private final RedisConnectionFactory redisConnectionFactory;

    private final MbootClientDetailService mbootClientDetailService;

    /**
     * 配置应用名称 应用id
     * 配置OAuth2的客户端相关信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(mbootClientDetailService);
        mbootClientDetailService.loadAllClientToCache();
    }

    /**
     * token认证器
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancer())
                .userDetailsService(mbootUserDetailService)
                .authenticationManager(authenticationManager)
                //是否重用refresh_token
                .reuseRefreshTokens(false)
                .exceptionTranslator(webResponseExceptionTranslator);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //让/oauth/token支持client_id以及client_secret作登录认证
                .allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * token存储
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(SecurityConstants.MBOOT_PREFIX + SecurityConstants.OAUTH_PREFIX);
        tokenStore.setAuthenticationKeyGenerator(new DefaultAuthenticationKeyGenerator() {
            @Override
            public String extractKey(OAuth2Authentication authentication) {
                return super.extractKey(authentication) + ":" + TenantContextHolder.getTenantId();
            }
        });
        return tokenStore;
    }


    /**
     * token增强，客户端模式不增强。
     *
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            if (SecurityConstants.CLIENT_CREDENTIALS
                    .equals(authentication.getOAuth2Request().getGrantType())) {
                return accessToken;
            }
            final Map<String, Object> additionalInfo = new HashMap<>(8);
            MbootLoginUser loginUser = (MbootLoginUser) authentication.getUserAuthentication().getPrincipal();
            additionalInfo.put(SecurityConstants.DETAILS_USER_ID, loginUser.getId());
            additionalInfo.put(SecurityConstants.DETAILS_USERNAME, loginUser.getUsername());
            additionalInfo.put(SecurityConstants.DETAILS_DEPT_ID, loginUser.getDeptId());
            additionalInfo.put(SecurityConstants.DETAILS_TENANT_ID, loginUser.getTenantId());
            additionalInfo.put(SecurityConstants.DETAILS_LICENSE, SecurityConstants.MBOOT_LICENSE);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}

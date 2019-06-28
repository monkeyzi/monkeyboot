package com.monkeyzi.mboot.security.mobile;

import com.monkeyzi.mboot.common.core.constant.SecurityConstants;
import com.monkeyzi.mboot.security.config.resource.MbootAuthExceptionEntryPoint;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 手机号登录验证 filter
 */
@Slf4j
public class MobileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";
    private static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    @Getter
    @Setter
    private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;
    @Getter
    @Setter
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    @Getter
    @Setter
    private boolean postOnly = true;
    @Getter
    @Setter
    private AuthenticationEventPublisher eventPublisher;
    @Getter
    @Setter
    private MbootAuthExceptionEntryPoint authenticationEntryPoint;


    protected MobileAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.SMS_TOKEN_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest  request,
                                                HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        //只允许post方式
        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String mobile=getMobilePara(request);
        String password=getPasswordPara(request);
        MobileAuthenticationToken mobileAuthenticationToken = new MobileAuthenticationToken(mobile,password);
        mobileAuthenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
        Authentication authResult=null;
        try {
            authResult = this.getAuthenticationManager().authenticate(mobileAuthenticationToken);
            log.debug("Authentication success");
            SecurityContextHolder.getContext().setAuthentication(authResult);
        }catch (Exception e){
            logger.debug("Authentication request failed: " + e);
            SecurityContextHolder.clearContext();
            logger.debug("Authentication request failed: " + e);
            eventPublisher.publishAuthenticationFailure(new BadCredentialsException(e.getMessage(), e),
                    new PreAuthenticatedAuthenticationToken("access-token", "N/A"));
            try {
                authenticationEntryPoint.commence(request, response,
                        new UsernameNotFoundException(e.getMessage(), e));
            } catch (Exception err) {
                logger.error("authenticationEntryPoint handle error:{}", err);
            }
        }
        return authResult;
    }

    private String getMobilePara(HttpServletRequest request){
         return request.getParameter(mobileParameter);
    }
    private String getPasswordPara(HttpServletRequest request){
        return request.getParameter(passwordParameter);
    }
}

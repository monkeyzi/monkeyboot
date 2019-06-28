package com.monkeyzi.mboot.security.mobile;

import com.monkeyzi.mboot.security.service.MbootUserDetailService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 手机号密码登录 authentication
 */
@Setter
@Slf4j
public class MobileAuthenticationProvider implements AuthenticationProvider {
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private MbootUserDetailService mbootUserDetailService;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken= (MobileAuthenticationToken) authentication;
        String mobile= (String) mobileAuthenticationToken.getPrincipal();
        String password= (String) mobileAuthenticationToken.getCredentials();
        UserDetails details=mbootUserDetailService.loadUserByMobilePhone(mobile);
        if (details==null){
            System.out.println("消息="+messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.noopBindAccount"));
                throw new BadCredentialsException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.noopBindAccount",
                        "NoopBindAccount"));
        }
        if (!passwordEncoder.matches(password, details.getPassword())) {
            throw new BadCredentialsException("手机号或密码错误");
        }
        MobileAuthenticationToken authenticationResult = new MobileAuthenticationToken(details, password, details.getAuthorities());
        authenticationResult.setDetails(mobileAuthenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return MobileAuthenticationToken.class.isAssignableFrom(aClass);
    }
}

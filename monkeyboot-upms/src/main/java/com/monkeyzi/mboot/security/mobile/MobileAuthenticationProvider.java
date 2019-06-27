package com.monkeyzi.mboot.security.mobile;

import com.monkeyzi.mboot.security.service.MbootUserDetailService;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 手机号密码登录 authentication
 */
@Setter
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private MbootUserDetailService mbootUserDetailService;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken= (MobileAuthenticationToken) authentication;
        String mobile= (String) mobileAuthenticationToken.getPrincipal();
        String password= (String) mobileAuthenticationToken.getCredentials();
        UserDetails details=mbootUserDetailService.loadUserByMobilePhone(mobile);
        if (details==null){
            throw new InternalAuthenticationServiceException("手机号或密码错误");
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

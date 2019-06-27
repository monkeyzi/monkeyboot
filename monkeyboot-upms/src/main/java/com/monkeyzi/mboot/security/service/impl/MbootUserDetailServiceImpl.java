package com.monkeyzi.mboot.security.service.impl;

import com.monkeyzi.mboot.security.entity.MbootLoginUser;
import com.monkeyzi.mboot.security.service.MbootUserDetailService;
import com.monkeyzi.mboot.service.MbootUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author: 高yg
 * @date: 2019/6/26 21:50
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Slf4j
@Service
public class MbootUserDetailServiceImpl implements MbootUserDetailService,SocialUserDetailsService {

    @Autowired
    private MbootUserService mbootUserService;

    /**
     * 根据手机号查找
     * @param mobile
     * @return
     */
    @Override
    public UserDetails loadUserByMobilePhone(String mobile)throws  UsernameNotFoundException {
        MbootLoginUser  loginUser=mbootUserService.getUserByMobilePhone(mobile);
        return checkLoginUser(loginUser);
    }

    /**
     * 根据用户名查找
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MbootLoginUser  loginUser=mbootUserService.getUserByUserName(username);
        if (loginUser==null){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        return checkLoginUser(loginUser);
    }

    /**
     * openId查找
     * @param openId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String openId) throws UsernameNotFoundException {
        MbootLoginUser  loginUser=mbootUserService.getUserByWxOpenId(openId);
        return checkLoginUser(loginUser);
    }


    private MbootLoginUser checkLoginUser(MbootLoginUser loginUser){
        if (loginUser!=null&&!loginUser.isEnabled()){
             throw new DisabledException("用户已经被禁用！");
        }
        return loginUser;
    }
}

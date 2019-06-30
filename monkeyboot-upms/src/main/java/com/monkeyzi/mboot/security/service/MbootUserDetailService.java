package com.monkeyzi.mboot.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author: 高yg
 * @date: 2019/6/26 21:46
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootUserDetailService extends UserDetailsService {

    UserDetails loadUserByMobilePhone(String mobile);

    UserDetails loadUserBySocialId(String wxOpenId);
}

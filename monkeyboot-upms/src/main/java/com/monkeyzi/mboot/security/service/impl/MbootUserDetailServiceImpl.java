package com.monkeyzi.mboot.security.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.monkeyzi.mboot.common.security.entity.MbootLoginUser;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.security.service.MbootUserDetailService;
import com.monkeyzi.mboot.service.MbootUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: 高yg
 * @date: 2019/6/26 21:50
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Slf4j
@Service
public class MbootUserDetailServiceImpl implements MbootUserDetailService{

    @Autowired
    private MbootUserService mbootUserService;

    /**
     * 根据手机号查找
     * @param mobile
     * @return
     */
    @Override
    public UserDetails loadUserByMobilePhone(String mobile)throws  UsernameNotFoundException {
        MbootUser loginUser=mbootUserService.getUserByMobilePhone(mobile);
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
        MbootUser  loginUser=mbootUserService.getUserByUserName(username);
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
    public UserDetails loadUserBySocialId(String openId) throws UsernameNotFoundException {
        MbootUser  loginUser=mbootUserService.getUserByWxOpenId(openId);
        return checkLoginUser(loginUser);
    }


    private UserDetails checkLoginUser(MbootUser loginUser){
        Set<String> dbAuthsSet = new HashSet<>();
        if (CollectionUtil.isNotEmpty(loginUser.getRoleList())) {
            // 获取角色
            loginUser.getRoleList().forEach(role -> dbAuthsSet.add(role.getRoleCode()));
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(loginUser.getPermissions()));

        }
        Collection<? extends GrantedAuthority> authorities
                = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
        boolean enabled = loginUser.getStatus()==0;
        return new MbootLoginUser(loginUser.getId(),loginUser.getDeptId(),
                loginUser.getTenantId(),loginUser.getUsername(),loginUser.getPassword(),
                enabled,true,true,loginUser.getIsDel()==0,authorities);
    }
}

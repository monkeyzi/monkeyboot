package com.monkeyzi.mboot.common.security.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author: 高yg
 * @date: 2019/6/26 22:06
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Getter
public class MbootLoginUser extends User implements UserDetails{

    private Integer id;
    private Integer tenantId;
    private Integer deptId;

    /**
     *
     * @param id       用户Id
     * @param deptId   部门Id
     * @param tenantId 租户Id
     * @param username 用户名
     * @param password 密码
     * @param enabled  true
     * @param accountNonExpired true
     * @param credentialsNonExpired true
     * @param accountNonLocked  根据用户的状态设置
     * @param authorities  权限
     */
    public MbootLoginUser(Integer id, Integer deptId, Integer tenantId,
                          String username, String password,
                          boolean enabled,
                          boolean accountNonExpired,
                          boolean credentialsNonExpired,
                          boolean accountNonLocked,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id=id;
        this.deptId=deptId;
        this.tenantId=tenantId;
    }
}

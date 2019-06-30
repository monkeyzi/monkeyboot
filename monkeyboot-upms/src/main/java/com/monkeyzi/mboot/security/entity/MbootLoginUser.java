package com.monkeyzi.mboot.security.entity;

import com.monkeyzi.mboot.entity.MbootUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author: 高yg
 * @date: 2019/6/26 22:06
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Getter
@Setter
public class MbootLoginUser extends MbootUser  implements UserDetails {

    /**
     * 权限标识集合
     */
    private String[] permissions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        if (!CollectionUtils.isEmpty(super.getRoleList())) {
            super.getRoleList().parallelStream().forEach(role -> collection.add(new SimpleGrantedAuthority(role.getRoleCode())));
        }
        return collection;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getStatus()==0;
    }
}

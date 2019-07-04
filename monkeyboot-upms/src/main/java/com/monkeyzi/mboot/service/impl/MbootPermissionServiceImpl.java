package com.monkeyzi.mboot.service.impl;

import com.monkeyzi.mboot.common.core.constant.SecurityConstants;
import com.monkeyzi.mboot.common.core.dto.PermissionTreeDto;
import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.common.core.utils.TreeUtil;
import com.monkeyzi.mboot.common.security.utils.SecurityUtils;
import com.monkeyzi.mboot.entity.MbootDept;
import com.monkeyzi.mboot.entity.MbootPermission;
import com.monkeyzi.mboot.mapper.MbootDeptMapper;
import com.monkeyzi.mboot.mapper.MbootPermissionMapper;
import com.monkeyzi.mboot.service.MbootDeptService;
import com.monkeyzi.mboot.service.MbootPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
public class MbootPermissionServiceImpl extends SuperServiceImpl<MbootPermissionMapper,MbootPermission> implements MbootPermissionService {

    @Autowired
    private MbootPermissionMapper mbootPermissionMapper;


    @Override
    public List<MbootPermission> getPermissionsByRoleId(Integer roleId) {
        return mbootPermissionMapper.selectPermissionsByRoleId(roleId);
    }

    @Override
    public List<PermissionTreeDto> getUserOwnPermissions() {
        Integer userId=SecurityUtils.getLoginUser().getId();
        //查询用户的权限
        List<MbootPermission> permissions=mbootPermissionMapper.selectPermissionByUserId(userId);
        List<PermissionTreeDto> menuTreeList = permissions.stream()
                .filter(permission -> SecurityConstants.MENU==permission.getType())
                .map(p->{
                    PermissionTreeDto dto=new PermissionTreeDto();
                    ModelMapper mapper=new ModelMapper();
                    mapper.map(p,dto);
                    return dto;
                })
                .sorted(Comparator.comparingInt(PermissionTreeDto::getSort))
                .collect(Collectors.toList());
        return TreeUtil.build(menuTreeList,SecurityConstants.MENU_ROOT);
    }
}

package com.monkeyzi.mboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.monkeyzi.mboot.common.core.constant.SecurityConstants;
import com.monkeyzi.mboot.common.core.dto.PermissionTreeDto;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.common.core.utils.TreeUtil;
import com.monkeyzi.mboot.common.security.utils.SecurityUtils;
import com.monkeyzi.mboot.entity.MbootDept;
import com.monkeyzi.mboot.entity.MbootPermission;
import com.monkeyzi.mboot.entity.MbootRolePermission;
import com.monkeyzi.mboot.enums.DelStatusEnum;
import com.monkeyzi.mboot.mapper.MbootDeptMapper;
import com.monkeyzi.mboot.mapper.MbootPermissionMapper;
import com.monkeyzi.mboot.protocal.req.permission.PermissionSaveReq;
import com.monkeyzi.mboot.service.MbootDeptService;
import com.monkeyzi.mboot.service.MbootPermissionService;
import com.monkeyzi.mboot.service.MbootRolePermissionService;
import com.monkeyzi.mboot.utils.util.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private MbootRolePermissionService mbootRolePermissionService;


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
                .filter(permission -> SecurityConstants.MENU.equals(permission.getType()))
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

    @Override
    public List<PermissionTreeDto> getPermissionsTreeByRoleId(Integer roleId) {
        //先查询系统中所有的菜单
        List<MbootPermission> list=this.list(new QueryWrapper<MbootPermission>().lambda().eq(MbootPermission::getIsDel,
                DelStatusEnum.IS_NOT_DEL.getType()));
        //查询该角色所拥有的菜单权限
        List<MbootPermission> rolePermissions=this.getPermissionsByRoleId(roleId);
        ModelMapper modelMapper=new ModelMapper();
        List<PermissionTreeDto> dtoList=modelMapper.map(list,new TypeToken<List<PermissionTreeDto>>() {}.getType());
        dtoList.forEach(a->{
            for (MbootPermission permission:rolePermissions){
                if (a.getId().equals(permission.getId())){
                     a.setCheck(Boolean.TRUE);
                     break;
                }
            }
        });
        return TreeUtil.build(dtoList,SecurityConstants.MENU_ROOT);
    }

    @Override
    public List<PermissionTreeDto> getAllPermissionsTree() {
        //先查询系统中所有的菜单
        List<MbootPermission> list=this.list(new QueryWrapper<MbootPermission>().lambda().eq(MbootPermission::getIsDel,
                DelStatusEnum.IS_NOT_DEL.getType()));
        ModelMapper modelMapper=new ModelMapper();
        List<PermissionTreeDto> dtoList=modelMapper.map(list,new TypeToken<List<PermissionTreeDto>>() {}.getType());
        return TreeUtil.build(dtoList,SecurityConstants.MENU_ROOT);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R removePermissionById(Integer id) {
        //查询该菜单下有没有子菜单
        List<MbootPermission> permissions = this.list(Wrappers.<MbootPermission>query()
                .lambda().eq(MbootPermission::getParentId, id)
        .eq(MbootPermission::getIsDel,DelStatusEnum.IS_NOT_DEL.getType()));
        if (PublicUtil.isNotEmpty(permissions)){
            return R.errorMsg("该菜单下有子菜单，不能删除！");
        }
        //删除角色权限表数据
        this.mbootRolePermissionService.remove(new QueryWrapper<MbootRolePermission>().lambda()
                .eq(MbootRolePermission::getPermissionId,id));
        //删除菜单权限
        this.removeById(id);
        return R.ok("删除成功！");
    }

    @Override
    public R savePermission(PermissionSaveReq req) {
        MbootPermission permission=new MbootPermission();
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.map(req,permission);
        permission.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        this.save(permission);
        return R.okMsg("新增成功！");
    }

    @Override
    public R editPermission(PermissionSaveReq req) {
        MbootPermission permission=new MbootPermission();
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.map(req,permission);
        permission.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        this.updateById(permission);
        return R.okMsg("修改成功！");
    }


}

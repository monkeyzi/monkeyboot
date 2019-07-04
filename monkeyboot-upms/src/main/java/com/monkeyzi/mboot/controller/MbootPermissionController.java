package com.monkeyzi.mboot.controller;

import com.monkeyzi.mboot.common.core.dto.PermissionTreeDto;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.entity.MbootPermission;
import com.monkeyzi.mboot.protocal.req.permission.PermissionSaveReq;
import com.monkeyzi.mboot.service.MbootPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: 高yg
 * @date: 2019/6/23 20:44
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:菜单权限管理
 */
@RestController
@Slf4j
@RequestMapping(value = "/permission")
public class MbootPermissionController {

    @Autowired
    private MbootPermissionService mbootPermissionService;

    /**
     * 获取当前用户页面菜单权限(用于导航展示)
     * @return
     */
    @GetMapping(value = "/selfPermission")
    public R getUserOwnPermissions(){
        List<PermissionTreeDto> list= mbootPermissionService.getUserOwnPermissions();
        return R.ok("ok",list);
    }

    /**
     * 查询角色的权限树
     * @param roleId
     * @return
     */
    @GetMapping(value = "/tree/{roleId}")
    public R getPermissionsTreeByRoleId(@PathVariable Integer roleId){
        log.info("查询角色权限树的参数 param={}",roleId);
        List<PermissionTreeDto> list=mbootPermissionService.getPermissionsTreeByRoleId(roleId);
        return R.ok("ok",list);
    }

    /**
     * 菜单树
     * @return
     */
    @GetMapping(value = "/tree")
    public R getAllPermissionsTree(){
        List<PermissionTreeDto> list=mbootPermissionService.getAllPermissionsTree();
        return R.ok("ok",list);
    }

    /**
     * 查询菜单详情
     * @param id
     * @return
     */
    @GetMapping(value = "/detail/{id}")
    public R permissionDetail(@PathVariable Integer id){
        log.info("查询菜单详情的参数 param={}",id);
        MbootPermission permission=mbootPermissionService.getById(id);
        return R.ok("ok",permission);
    }

    /**
     * 新增菜单
     * @param req
     * @return
     */
    @PostMapping(value = "/save")
    public R save(@RequestBody @Valid PermissionSaveReq req){
        log.info("新增菜单的参数为 param={}",req);
        R r=mbootPermissionService.savePermission(req);
        return r;
    }

    /**
     * 修改菜单
     * @param req
     * @return
     */
    @PutMapping(value = "/edit")
    public R edit(@RequestBody @Valid PermissionSaveReq req){
        log.info("修改菜单的参数为 param={}",req);
        R r=mbootPermissionService.editPermission(req);
        return r;
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @PostMapping(value = "/del/{id}")
    public R removePermissionById(@PathVariable Integer id){
        log.info("删除菜单权限的参数为 param={}",id);
        R r=mbootPermissionService.removePermissionById(id);
        return r;
    }
}

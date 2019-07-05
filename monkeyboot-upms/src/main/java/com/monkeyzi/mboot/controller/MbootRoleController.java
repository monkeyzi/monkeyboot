package com.monkeyzi.mboot.controller;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.common.security.utils.SecurityUtils;
import com.monkeyzi.mboot.entity.MbootRole;
import com.monkeyzi.mboot.protocal.req.RolePageReq;
import com.monkeyzi.mboot.service.MbootRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/23 20:42
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description: 角色管理
 */
@RestController
@Slf4j
@RequestMapping(value = "/role")
@Api(description = "角色管理",value = "role")
public class MbootRoleController {

    @Autowired
    private MbootRoleService mbootRoleService;

    @ApiOperation(value = "分页查询角色")
    @GetMapping(value = "/page")
    @PreAuthorize("@pms.hasPermission('role_page')")
    public R  page(@Valid RolePageReq req){
        log.info("分页查询角色列表的参数为 param={}",req);
        PageInfo pageInfo=mbootRoleService.listPageRoleByCondition(req);
        return R.ok("ok",pageInfo);
    }

    @ApiOperation(value = "查询角色详情")
    @GetMapping(value = "/detail/{id}")
    public R  roleDetailInfo(@PathVariable Integer id){
        log.info("根据Id查询角色信息的参数为 param={}",id);
        MbootRole role =mbootRoleService.getById(id);
        return R.ok("ok",role);
    }


    @ApiOperation(value = "保存角色")
    @PostMapping(value = "/save")
    public R  saveRole(@Valid @RequestBody MbootRole role){
        log.info("新增角色的参数为 param={}",role);
        role.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        boolean flag=mbootRoleService.save(role);
        if (flag){
            return R.errorMsg("新增成功！");
        }else {
            return R.errorMsg("新增失败！");
        }
    }

    @ApiOperation(value = "修改角色")
    @PutMapping(value = "/edit")
    public R  editRole(@Valid @RequestBody MbootRole role){
        log.info("修改角色的参数为 param={}",role);
        role.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        boolean flag=mbootRoleService.updateById(role);
        if (flag){
            return R.errorMsg("修改成功！");
        }else {
            return R.errorMsg("修改失败！");
        }
    }

    @ApiOperation(value = "查询系统所有的角色列表")
    @GetMapping(value = "/list")
    public R  list(){
        List<MbootRole>  lists=mbootRoleService.listAllRoles();
        return R.ok("ok",lists);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping(value = "/delRole/{id}")
    public R  delRoleById(@PathVariable Integer id){
        log.info("删除角色的参数为 param={}",id);
        boolean flag=mbootRoleService.delRoleById(id);
        if (flag){
            return R.errorMsg("删除成功！");
        }else {
            return R.errorMsg("删除失败！");
        }
    }

    @ApiOperation(value = "校验角色code是否存在")
    @GetMapping(value = "/check/{roleCode}")
    public R  checkRoleCode(@PathVariable String roleCode){
        log.info("校验角色表示唯一性参数为 param={}",roleCode);
        Integer count=mbootRoleService.checkRoleCode(roleCode);
        if (count>0){
            return R.errorMsg("角色标识已存在");
        }else {
            return R.ok();
        }

    }

    @ApiOperation(value = "给角色绑定菜单权限")
    @PutMapping(value = "/roleBindPermission")
    public R roleBindPermission(@NotNull(message = "角色Id不能为空") @RequestParam Integer roleId,
                                @NotEmpty(message = "权限Id列表不能为空")@RequestParam List<Integer> permissions){
        log.info("给角色绑定菜单权限的参数为 roleId={},permissions={}",roleId,permissions);
        R result=mbootRoleService.roleBindPermission(roleId,permissions);
        return result;
    }
}

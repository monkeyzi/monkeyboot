package com.monkeyzi.mboot.controller;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.entity.MbootRole;
import com.monkeyzi.mboot.protocal.req.RolePageReq;
import com.monkeyzi.mboot.service.MbootRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
public class MbootRoleController {

    @Autowired
    private MbootRoleService mbootRoleService;

    @GetMapping(value = "/page")
    @PreAuthorize("@pms.hasPermission('role_page')")
    public R  page(@Valid RolePageReq req){
        log.info("分页查询角色列表的参数为 param={}",req);
        PageInfo pageInfo=mbootRoleService.listPageRoleByCondition(req);
        return R.ok("ok",pageInfo);
    }

    @GetMapping(value = "/detail/{id}")
    public R  roleDetailInfo(@PathVariable Integer id){
        log.info("根据Id查询角色信息的参数为 param={}",id);
        MbootRole role =mbootRoleService.getById(id);
        return R.ok("ok",role);
    }


    @PostMapping(value = "/save")
    public R  saveRole(@Valid @RequestBody MbootRole role){
        log.info("新增角色的参数为 param={}",role);
        boolean flag=mbootRoleService.save(role);
        if (flag){
            return R.errorMsg("新增成功！");
        }else {
            return R.errorMsg("新增失败！");
        }
    }

    @PostMapping(value = "/edit")
    public R  editRole(@Valid @RequestBody MbootRole role){
        log.info("修改角色的参数为 param={}",role);
        boolean flag=mbootRoleService.updateById(role);
        if (flag){
            return R.errorMsg("修改成功！");
        }else {
            return R.errorMsg("修改失败！");
        }
    }

    @GetMapping(value = "/list")
    public R  list(){
        List<MbootRole>  lists=mbootRoleService.listAllRoles();
        return R.ok("ok",lists);
    }

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

    @PutMapping(value = "/roleBindPermission")
    public R roleBindPermission(@NotBlank(message = "角色Id不能为空") @RequestParam String roleId,
                                @NotEmpty(message = "权限Id列表不能为空")@RequestParam List<Integer> permissions){
        log.info("给角色绑定菜单权限的参数为 roleId={},permissions={}",roleId,permissions);
        R result=mbootRoleService.roleBindPermission(roleId,permissions);
        return result;
    }
}

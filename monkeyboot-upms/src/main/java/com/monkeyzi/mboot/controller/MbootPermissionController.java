package com.monkeyzi.mboot.controller;

import com.monkeyzi.mboot.common.core.dto.PermissionTreeDto;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.entity.MbootPermission;
import com.monkeyzi.mboot.service.MbootPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

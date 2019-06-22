package com.monkeyzi.mboot.controller;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.protocal.req.UserPageReq;
import com.monkeyzi.mboot.service.MbootUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author: 高yg
 * @date: 2019/6/19 22:06
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@RestController
@Slf4j
@RequestMapping(value = "/user")
public class MbootUserController {

    @Autowired
    private MbootUserService mbootUserService;

    @GetMapping(value = "/list")
    public R index(@Valid  UserPageReq req){
        log.info("分页查询用户列表的参数为 param={}",req);
        PageInfo pageInfo=mbootUserService.listPageUserByCondition(req);
        return R.ok("ok",pageInfo);
    }

    @GetMapping(value = "/holder")
    public R holder(){
        return R.okMsg("查询成功");
    }
}

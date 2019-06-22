package com.monkeyzi.mboot.controller;

import com.monkeyzi.mboot.common.core.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: 高yg
 * @date: 2019/6/19 22:06
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@RestController
@Slf4j
public class MbootUserController {

    @Resource
    private TaskExecutor taskExecutor;

    @GetMapping(value = "/index")
    public R index(){

        return R.ok("ok",null);
    }

    @GetMapping(value = "/holder")
    public R holder(){
        return R.okMsg("查询成功");
    }
}

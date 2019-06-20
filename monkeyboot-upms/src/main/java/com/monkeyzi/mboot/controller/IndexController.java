package com.monkeyzi.mboot.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.entity.Blog;
import com.monkeyzi.mboot.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/19 22:06
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@RestController
@Slf4j
public class IndexController {


    @Autowired
    private BlogService blogService;

    @Resource
    private TaskExecutor taskExecutor;

    @GetMapping(value = "/index")
    public R index(){
        PageHelper.startPage(1,5);
        List<Blog> list=blogService.list();
        PageInfo pageInfo=new PageInfo(list);
        taskExecutor.execute(()-> log.info("你好-----------"));
        return R.ok("ok",pageInfo);
    }
}

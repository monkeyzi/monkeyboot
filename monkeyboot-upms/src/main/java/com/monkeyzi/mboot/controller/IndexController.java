package com.monkeyzi.mboot.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.entity.Blog;
import com.monkeyzi.mboot.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/19 22:06
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@RestController
public class IndexController {


    @Autowired
    private BlogService blogService;

    @GetMapping(value = "/index")
    public PageInfo  index(){
        PageHelper.startPage(1,5);
        List<Blog> list=blogService.list();
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }
}

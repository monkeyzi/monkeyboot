package com.monkeyzi.mboot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.UpmsApplicationTests;
import com.monkeyzi.mboot.entity.Blog;
import com.monkeyzi.mboot.mapper.BlogMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:04
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class BlogServicetest extends UpmsApplicationTests {


    @Autowired
    private BlogService blogService;

    @Test
    public void  test(){
        PageHelper.startPage(1,5);
        List<Blog> list=blogService.list();
        PageInfo pageInfo=new PageInfo(list);
        System.out.println(pageInfo);
    }
}

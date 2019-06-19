package com.monkeyzi.mboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 高yg
 * @date: 2019/6/19 22:06
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@RestController
public class IndexController {


    @GetMapping(value = "/index")
    public String  index(){
        return "hello world";
    }
}

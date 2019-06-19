package com.monkeyzi.mboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monkeyzi.mboot.entity.Blog;
import com.monkeyzi.mboot.mapper.BlogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
public class BlogServiceImpl extends ServiceImpl<BlogMapper,Blog> implements BlogService {
}

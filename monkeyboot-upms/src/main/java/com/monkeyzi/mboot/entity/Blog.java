package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:01
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@TableName(value = "blog_content")
public class Blog implements Serializable {

    @TableId
    private Integer blogId;

    private Integer userId;


}

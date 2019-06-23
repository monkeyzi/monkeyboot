package com.monkeyzi.mboot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 高yg
 * @date: 2019/4/14 22:20
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Getter
@AllArgsConstructor
public enum DelStatusEnum {

    /**
     * 删除状态：0未删除
     */
    IS_NOT_DEL(0, "未删除"),
    /**
     * 删除状态：1未删除
     */
    IS_DEL(1, "已删除");


    /**
     * 类型
     */
    private  Integer type;
    /**
     * 描述
     */
    private  String description;

}

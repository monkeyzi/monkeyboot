package com.monkeyzi.mboot.common.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author: 高yg
 * @date: 2019/6/20 20:45
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:mapper 父类，注意这个类不要让 mybatis-plus 扫描到！！
 */
public interface SuperMapper<T> extends BaseMapper<T> {
}

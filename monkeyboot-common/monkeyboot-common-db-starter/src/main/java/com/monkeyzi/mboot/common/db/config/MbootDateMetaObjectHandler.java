package com.monkeyzi.mboot.common.db.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author 高yg
 * @date 2019/6/22 11:15
 * @description  数据库时间 公共字段更新
 **/
public class MbootDateMetaObjectHandler  implements MetaObjectHandler {
    private final static String UPDATE_TIME = "updateTime";
    private final static String CREATE_TIME = "createTime";
    /**
     * 新增
     * @param metaObject
     */
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName(CREATE_TIME, metaObject);
        Object updateTime = getFieldValByName(UPDATE_TIME, metaObject);
        if (createTime == null || updateTime == null) {
            LocalDateTime date=LocalDateTime.now();
            if (createTime == null) {
                setFieldValByName(CREATE_TIME, date, metaObject);
            }
            if (updateTime == null) {
                setFieldValByName(UPDATE_TIME, date, metaObject);
            }
        }
    }

    /**
     * 更新
     * @param metaObject
     */
    public void updateFill(MetaObject metaObject) {
        LocalDateTime dateTime=LocalDateTime.now();
        setFieldValByName(UPDATE_TIME, dateTime, metaObject);
    }
}

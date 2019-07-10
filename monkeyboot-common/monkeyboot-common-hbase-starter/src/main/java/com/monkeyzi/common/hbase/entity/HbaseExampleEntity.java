package com.monkeyzi.common.hbase.entity;

import com.monkeyzi.common.hbase.column.HbaseColumn;
import com.monkeyzi.common.hbase.column.HbaseTable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author doubleM
 * @ClassName: HbaseExampleEntity
 * @Description: 与Hbase中表的rowkey、列簇、列名以及列对应
 * @date 2018年4月25日 下午2:13:26
 */
@Getter
@Setter
@HbaseTable(tableName = "uws_phone_history")
public class HbaseExampleEntity {

    /**
     * 标示数据中rowkey，用在新增时候填充
     */
    @HbaseColumn(family = "rowkey", qualifier = "rowkey")
    private String id;

    /**
     * 列簇、列映射的实体名称，用于查询的数据返回
     */
    @HbaseColumn(family = "cf", qualifier = "phone")
    private String phone;

    @HbaseColumn(family = "cf", qualifier = "sex")
    private String sex;

    @HbaseColumn(family = "cf", qualifier = "app")
    private String app;

    @HbaseColumn(family = "cf", qualifier = "device")
    private String device;

}

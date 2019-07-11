package com.monkeyzi.common.hbase.entity;

import com.monkeyzi.common.hbase.column.HbaseColumn;
import com.monkeyzi.common.hbase.column.HbaseTable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@HbaseTable(tableName = "uws_mac_history")
public class HbaseMacEntity implements Serializable {
    /**
     * 标示数据中rowkey，用在新增时候填充
     */
    @HbaseColumn(family = "rowkey", qualifier = "rowkey")
    private String id;

    /**
     * 列簇、列映射的实体名称，用于查询的数据返回
     */
    @HbaseColumn(family = "cf", qualifier = "mac")
    private String mac;

    @HbaseColumn(family = "cf", qualifier = "time")
    private String time;

    @HbaseColumn(family = "cf", qualifier = "day")
    private String day;

    @HbaseColumn(family = "cf", qualifier = "device")
    private String device;


}

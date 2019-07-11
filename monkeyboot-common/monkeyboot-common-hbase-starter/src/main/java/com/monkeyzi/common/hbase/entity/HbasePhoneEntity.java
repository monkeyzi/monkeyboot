package com.monkeyzi.common.hbase.entity;

import com.monkeyzi.common.hbase.column.HbaseColumn;
import com.monkeyzi.common.hbase.column.HbaseTable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author doubleM
 * @ClassName: HbasePhoneEntity
 * @Description: 与Hbase中表的rowkey、列簇、列名以及列对应
 * @date 2018年4月25日 下午2:13:26
 */
@Getter
@Setter
@HbaseTable(tableName = "uws_phone_history")
public class HbasePhoneEntity {

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

    @HbaseColumn(family = "cf", qualifier = "time")
    private String time;

    @HbaseColumn(family = "cf", qualifier = "age")
    private String age;

    @HbaseColumn(family = "cf", qualifier = "brand")
    private String brand;

    @HbaseColumn(family = "cf", qualifier = "city")
    private String city;

    @HbaseColumn(family = "cf", qualifier = "day")
    private String day;

    @HbaseColumn(family = "cf", qualifier = "idfa")
    private String idfa;

    @HbaseColumn(family = "cf", qualifier = "imei")
    private String imei;

    @HbaseColumn(family = "cf", qualifier = "mac")
    private String mac;

    @HbaseColumn(family = "cf", qualifier = "net")
    private String net;

    @HbaseColumn(family = "cf", qualifier = "province")
    private String province;

    @HbaseColumn(family = "cf", qualifier = "x")
    private String x;

    @HbaseColumn(family = "cf", qualifier = "y")
    private String y;

    @HbaseColumn(family = "cf", qualifier = "tag")
    private String tag;

}

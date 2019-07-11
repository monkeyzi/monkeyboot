package com.monkeyzi.common.hbase.template;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 封装hbase常用的操作类
 */
public interface HbaseOperations {
    /**
     * 创建表
     * @param tableName 表名字
     * @param familyColumn 列族
     * @return
     */
     boolean createTable(String tableName,Set<String> familyColumn);

    /**
     * 创建表
     * @param tableName
     * @param familyColumn
     * @param splitKeys
     * @return
     */
     boolean createTable(String tableName,Set<String> familyColumn,byte[][] splitKeys);

    /**
     * 删除表
     * @param tableName
     * @return
     */
     boolean deleteTable(String tableName);

    /**
     * 删除cf
     * @param tableName
     * @param familyColumn
     * @return
     */
     boolean deleteFamilyColumn(String tableName,String familyColumn);

    /**
     * 删除qualifier(删除指定rowKey的列族名为fc的某列qualifier)
     * @param tableName
     * @param familyColumn
     * @param qualifier
     * @param rowKey
     * @return
     */
     boolean delete(String tableName,String familyColumn,String qualifier,String rowKey);

     /**
     * 删除行
     * @param tableName
     * @param rowKey
     * @return
     */
     boolean delete(String tableName,String rowKey);

    /**
     * 根据rowkey查询结果
     * @param tableName
     * @param rowkey
     * @return
     */
     Result getRow(String tableName, String rowkey);

    /**
     * 根据rowKeys 列表查询
     * @param obj  返回的实体
     * @param rowKeys rowkey
     * @param <T>
     * @return
     */
     <T> List<T> getListByRowKeys(T obj, String ...rowKeys);

    /**
     * 返回hbase Result
     * @param tableName 表名
     * @param rowkeys   rowkeys
     * @return
     */
     List<Result> getResults(String tableName, String... rowkeys);

    /**
     * 根据表名获取所有的列族
     * @param tableName 表名
     * @return
     */
     List<String> familyColumns(String tableName);

    /**
     * habse添加数据(表名和列族等都在实体上)
     * @param objs
     * @param <T>
     * @return
     */
     <T> boolean save(List<T> objs);

    /**
     * 批量保存到hbase
     * @param puts
     * @param tableName
     * @return
     */
     boolean put(List<Put> puts, String tableName);

    /**
     * 删除row
     * @param obj
     * @param rowkeys
     * @param <T>
     */
     <T> void delete(T obj, String... rowkeys);

    /***
     * 删除
     * @param deletes
     * @param tableName
     */
     void delete(List<Delete> deletes, String tableName);

    /**
     * 保存记录
     * @param tableName
     * @param row
     * @param columnFamily
     * @param qualifier
     * @param data
     * @return
     */
     boolean put(String tableName, String row,
                   String columnFamily,
                   String qualifier, String data);

    /**
     * 保存数据
     * @param tableName
     * @param objs
     * @param <T>
     */
    <T> void put(String tableName, T... objs);

    /**
     * 获取某一列的值
     * @param tableName
     * @param rowKey
     * @param family
     * @param qualifier
     * @return
     */
    String getCellValue(String tableName, String rowKey, String family, String qualifier);

    /**
     * 条件查询----多个条件同时满足才会返回数据,条件之间是and的关系
     * @param obj
     * @param param
     * @param <T>
     * @return
     */
    <T> List<T> queryScan(T obj, Map<String, String> param);

    /**
     * 分页条件查询
     * @param tableName
     * @param family
     * @param param  普通条件查询map
     * @param param2 需要正则条件查询map
     * @param startKey
     * @param endKey
     * @param pageSize
     * @return
     */
    List<Result> queryScan(String tableName,String family,
                           Map<String,String> param,Map<String,String> param2,
                           String startKey,String endKey,Integer pageSize);



}

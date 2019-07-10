package com.monkeyzi.common.hbase.beanutil;

import com.monkeyzi.common.hbase.column.HbaseColumn;
import com.monkeyzi.common.hbase.column.HbaseTable;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@UtilityClass
@Slf4j
public class HbaseBeanUtil {


    /**
     * 转化Id 和rowKey
     * @param obj
     * @param <T>
     * @return
     */
    public <T> String parseObjId(T obj){
       Class<?> clazz=obj.getClass();
        try {
            // 实体中的ID就是hbase中的rowkey
            Field field = clazz.getDeclaredField("id");
            field.setAccessible(true);
            Object object = field.get(obj);
            return object.toString();
        } catch (NoSuchFieldException e) {
            log.error("", e);
        } catch (SecurityException e) {
            log.error("", e);
        } catch (IllegalArgumentException e) {
            log.error("", e);
        } catch (IllegalAccessException e) {
            log.error("", e);
        }
        return "";
    }

    /**
     * 将实体bean转为put
     * @param obj
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> Put beanToPut(T obj) throws Exception{
        Put put=new Put(Bytes.toBytes(parseObjId(obj)));
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(HbaseColumn.class)) {
                continue;
            }
            field.setAccessible(true);
            HbaseColumn orm = field.getAnnotation(HbaseColumn.class);
            String family = orm.family();
            String qualifier = orm.qualifier();
            if (StringUtils.isBlank(family) || StringUtils.isBlank(qualifier)) {
                continue;
            }
            Object fieldObj = field.get(obj);
            if (fieldObj.getClass().isArray()) {
                log.error("nonsupport");
            }
            // rowkey不获取
            if ("rowkey".equalsIgnoreCase(qualifier) || "rowkey".equalsIgnoreCase(family)) {
                continue;
            }
            if (field.get(obj) != null || StringUtils.isNotBlank(field.get(obj).toString())) {
                put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(field.get(obj).toString()));
            }
        }
        return put;
    }



    /**
     * 根据注解获取表名称
     * @param obj
     * @return
     */
    public String  getTable(Object obj){
        HbaseTable table = obj.getClass().getAnnotation(HbaseTable.class);
        return table.tableName();
    }


    /**
     * HBase result 转换为 bean
     *
     * @param result 结果集
     * @param obj    实体值
     * @param <T>
     * @return 返回转换好的结果实体
     * @throws Exception 系统异常
     */
     public <T> T resultToBean(Result result, T obj) throws Exception {
        if (result == null) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(HbaseColumn.class)) {
                continue;
            }
            HbaseColumn orm = field.getAnnotation(HbaseColumn.class);
            String family = orm.family();
            String qualifier = orm.qualifier();
            boolean timeStamp = orm.timestamp();
            if (StringUtils.isBlank(family) || StringUtils.isBlank(qualifier)) {
                continue;
            }
            String fieldName = field.getName();
            String value = "";
            if ("rowkey".equalsIgnoreCase(family)) {
                value = new String(result.getRow());
            } else {
                value = getResultValueByType(result, family, qualifier, timeStamp);
            }
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String setMethodName = "set" + firstLetter + fieldName.substring(1);
            Method setMethod = clazz.getMethod(setMethodName, new Class[]{field.getType()});
            setMethod.invoke(obj, new Object[]{value});
        }
        return obj;
    }

    /**
     * 返回结果对应值得类型
     *
     * @param result    结果集
     * @param family    列簇
     * @param qualifier 列名
     * @param timeStamp 时间戳
     * @return 字符串
     */
    public static String getResultValueByType(Result result, String family, String qualifier, boolean timeStamp) {
        if (!timeStamp) {
            return new String(result.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifier)));
        }
        List<Cell> cells = result.getColumnCells(Bytes.toBytes(family), Bytes.toBytes(qualifier));
        if (cells.size() == 1) {
            Cell cell = cells.get(0);
            return cell.getTimestamp() + "";
        }
        return "";
    }
}

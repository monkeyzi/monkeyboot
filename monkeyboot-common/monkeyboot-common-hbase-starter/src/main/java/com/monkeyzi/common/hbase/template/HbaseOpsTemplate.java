package com.monkeyzi.common.hbase.template;

import com.monkeyzi.common.hbase.beanutil.HbaseBeanUtil;
import com.monkeyzi.common.hbase.column.HbaseColumn;
import com.monkeyzi.common.hbase.config.HbaseConnectFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
public class HbaseOpsTemplate  implements HbaseOperations{
    public HbaseOpsTemplate(){
    }
    public boolean createTable(String tableName, Set<String> familyColumn) {
        try (HBaseAdmin admin= (HBaseAdmin) HbaseConnectFactory.connection.getAdmin()){
            TableName tn=TableName.valueOf(tableName);
            if (admin.tableExists(tn)){
                log.warn("hbase表={}创建失败，表已经存在",tableName);
                return false;
            }
            HTableDescriptor hd=new HTableDescriptor(tn);
            for (String  fc:familyColumn){
                HColumnDescriptor hc=new HColumnDescriptor(fc);
                hd.addFamily(hc);
            }
            admin.createTable(hd);
        }catch (Exception e){
            e.printStackTrace();
            log.error("hbase表={}创建失败 err={}",tableName,e);
            return false;
        }
        return true;
    }

    public boolean createTable(String tableName, Set<String> familyColumn, byte[][] splitKeys) {
        try (HBaseAdmin admin= (HBaseAdmin) HbaseConnectFactory.connection.getAdmin()){
            TableName tn=TableName.valueOf(tableName);
            if (admin.tableExists(tn)){
                log.warn("hbase表={}创建失败，表已经存在",tableName);
                return false;
            }
            HTableDescriptor hd=new HTableDescriptor(tn);
            for (String  fc:familyColumn){
                HColumnDescriptor hc=new HColumnDescriptor(fc);
                hd.addFamily(hc);
            }
            admin.createTable(hd,splitKeys);
        }catch (Exception e){
            e.printStackTrace();
            log.error("hbase表={}创建失败 err={}",tableName,e);
            return false;
        }
        return true;
    }

    public boolean deleteTable(String tableName) {
        try(HBaseAdmin admin= (HBaseAdmin) HbaseConnectFactory.connection.getAdmin()) {
            TableName tn=TableName.valueOf(tableName);
            admin.disableTable(tn);
            admin.deleteTable(tn);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除表={}出现异常 e={}",tableName,e);
            return false;
        }
        return true;
    }

    public boolean deleteFamilyColumn(String tableName, String familyColumn) {
        try(HBaseAdmin admin= (HBaseAdmin) HbaseConnectFactory.connection.getAdmin()) {
            admin.deleteColumn(TableName.valueOf(tableName),familyColumn.getBytes());
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除表={}列族={}出现异常 e={}",tableName,familyColumn,e);
            return false;
        }
        return true;
    }

    public boolean delete(String tableName, String familyColumn, String qualifier, String rowKey) {
        try(Table table=HbaseConnectFactory.connection.getTable(TableName.valueOf(tableName)) ) {
            Delete delete = new Delete(rowKey.getBytes());
            delete.addColumns(familyColumn.getBytes(), qualifier.getBytes());
            table.delete(delete);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除表={}列族={}列={},出现异常 e={}",tableName,familyColumn,qualifier,e);
            return false;
        }
        return true;
    }

    public boolean delete(String tableName, String rowKey) {
        try(Table table=HbaseConnectFactory.connection.getTable(TableName.valueOf(tableName)) ) {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除表={}rowKey={},出现异常 e={}",tableName,rowKey,e);
            return false;
        }
        return true;
    }

    public Result getRow(String tableName, String rowKey) {
        Result rs;
        try (Table table = HbaseConnectFactory.connection.getTable(TableName.valueOf(tableName))) {
            Get g = new Get(Bytes.toBytes(rowKey));
            rs = table.get(g);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("根据rowKey={}查询数据异常 e={}",rowKey,e);
            return null;
        }
        return rs;
    }

    public <T> List<T> getListByRowKeys(T obj, String ...rowKeys){
        List<T> lists = new ArrayList<>();
        String tableName = HbaseBeanUtil.getTable(obj);
        if (StringUtils.isBlank(tableName)) {
            return lists;
        }
        try (Table table = HbaseConnectFactory.connection.getTable(TableName.valueOf(tableName));
             Admin admin = HbaseConnectFactory.connection.getAdmin()) {
            if (!admin.isTableAvailable(TableName.valueOf(tableName))) {
                return lists;
            }
            List<Result> results = this.getResults(tableName, rowKeys);
            if (results.isEmpty()) {
                return lists;
            }
            for (int i = 0; i < results.size(); i++) {
                T bean = null;
                Result result = results.get(i);
                if (result == null || result.isEmpty()) {
                    continue;
                }
                try {
                    bean = HbaseBeanUtil.resultToBean(result, obj);
                    lists.add(bean);
                } catch (Exception e) {
                    log.error("查询异常 rowKey={},e={}！",rowKeys, e);
                }
            }
        } catch (Exception e) {
            log.error("查询异常 rowKey={},e={}",rowKeys,e);
        }
        return lists;
    }

    @Override
    public List<Result> getResults(String tableName, String... rowkeys) {
        List<Result> resultList = new ArrayList<>();
        List<Get> gets = new ArrayList<>();
        for (String rowkey : rowkeys) {
            if (StringUtils.isBlank(rowkey)) {
                continue;
            }
            Get get = new Get(Bytes.toBytes(rowkey));
            gets.add(get);
        }
        try (Table table = HbaseConnectFactory.connection.getTable(TableName.valueOf(tableName));) {
            Result[] results = table.get(gets);
            Collections.addAll(resultList, results);
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return resultList;
        }
    }

    @Override
    public List<String> familyColumns(String tableName) {
        try (Table table = HbaseConnectFactory.connection.getTable(TableName.valueOf(tableName))) {
            List<String> columns = new ArrayList<String>();
            if (table == null) {
                return columns;
            }
            HTableDescriptor tableDescriptor = table.getTableDescriptor();
            HColumnDescriptor[] columnDescriptors = tableDescriptor.getColumnFamilies();
            for (HColumnDescriptor columnDescriptor : columnDescriptors) {
                String columnName = columnDescriptor.getNameAsString();
                columns.add(columnName);
            }
            return columns;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列簇名称失败 tableName={},e={}！",tableName, e);
            return new ArrayList<>();
        }
    }

    @Override
    public <T> boolean save(List<T> objs) {
        List<Put> puts=new ArrayList<>();
        String tableName="";
        try (HBaseAdmin admin= (HBaseAdmin) HbaseConnectFactory.connection.getAdmin()) {
            for (Object obj:objs){
                if (obj==null){
                    continue;
                }
                tableName=HbaseBeanUtil.getTable(obj);
                if (!admin.tableExists(tableName)){
                    // 获取family, 创建表
                    Class<?> clazz = obj.getClass();
                    Field[] fields = clazz.getDeclaredFields();
                    Set<String> set = new HashSet<>(16);
                    for (int i = 0; i < fields.length; i++) {
                        if (!fields[i].isAnnotationPresent(HbaseColumn.class)) {
                            continue;
                        }
                        fields[i].setAccessible(true);
                        HbaseColumn orm = fields[i].getAnnotation(HbaseColumn.class);
                        String family = orm.family();
                        // 单独获取rowkey
                        if ("rowkey".equalsIgnoreCase(family)) {
                            continue;
                        }
                        set.add(family);
                    }
                    this.createTable(tableName,set);
                }
                Put put = HbaseBeanUtil.beanToPut(obj);
                puts.add(put);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("保存数据到hbase出现异常 e={}",e);
            return false;
        }
        return this.put(puts,tableName);
    }


    public boolean put(List<Put> puts, String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return false;
        }
        try (Table table = HbaseConnectFactory.connection.getTable(TableName.valueOf(tableName))) {
            table.put(puts);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public <T> void delete(T obj, String... rowkeys) {
        String tableName = HbaseBeanUtil.getTable(obj);
        if (StringUtils.isBlank(tableName)) {
            return;
        }
        List<Delete> deletes = new ArrayList<Delete>();
        for (String rowkey : rowkeys) {
            if (StringUtils.isBlank(rowkey)) {
                continue;
            }
            deletes.add(new Delete(Bytes.toBytes(rowkey)));
        }
        this.delete(deletes, tableName);
    }

    @Override
    public void delete(List<Delete> deletes, String tableName) {
        try (Table table = HbaseConnectFactory.connection.getTable(TableName.valueOf(tableName));) {
            if (StringUtils.isBlank(tableName)) {
                return;
            }
            table.delete(deletes);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("删除失败！ tableName={},e={}", tableName,e);
        }
    }

    @Override
    public boolean put(String tableName, String row, String columnFamily, String qualifier, String data) {
        try(Table table=HbaseConnectFactory.connection.getTable(TableName.valueOf(tableName))) {
            Put put = new Put(Bytes.toBytes(row));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier),
                    Bytes.toBytes(data));
            table.put(put);
        } catch (Exception e) {
           log.error("hbase新增数据失败 tableName={},rowKey={},cf={},qf={},data={}",tableName,
                   row,columnFamily,qualifier,data);
           return false;
        }
        return true;
    }

    @Override
    public <T> void put(String tableName, T... objs) {
        List<Put> puts = new ArrayList<>();
        for (Object obj : objs) {
            if (obj == null) {
                continue;
            }
            try {
                Put put = HbaseBeanUtil.beanToPut(obj);
                puts.add(put);
            } catch (Exception e) {
                log.error("保存数据到hbase出现异常");
            }
        }
        put(puts, tableName);
    }

    @Override
    public String getCellValue(String tableName, String rowKey, String family, String qualifier) {
        try (Table table=HbaseConnectFactory.connection.getTable(TableName.valueOf(tableName))){
            Get get = new Get(rowKey.getBytes());
            get.addColumn(family.getBytes(), qualifier.getBytes());
            Result rs = table.get(get);
            Cell cell = rs.getColumnLatestCell(family.getBytes(), qualifier.getBytes());
            String value = null;
            if (cell!=null) {
                value = Bytes.toString(CellUtil.cloneValue(cell));
            }
            return value;
        }catch (Exception e){
            log.error("hbase获取某一列的值失败了 tableName={},rowKey={},family={},qf={},e={}",
                    tableName,rowKey,family,qualifier,e);
            return null;
        }
    }

    @Override
    public <T> List<T> queryScan(T obj, Map<String, String> param) {
        return null;
    }




}

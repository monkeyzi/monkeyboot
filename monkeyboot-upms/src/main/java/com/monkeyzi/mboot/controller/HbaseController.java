package com.monkeyzi.mboot.controller;

import com.monkeyzi.common.hbase.entity.HbaseMacEntity;
import com.monkeyzi.common.hbase.entity.HbasePhoneEntity;
import com.monkeyzi.common.hbase.template.HbaseOpsTemplate;
import com.monkeyzi.mboot.common.core.result.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class HbaseController {

    @Autowired
    private HbaseOpsTemplate hbaseOpsTemplate;

    @GetMapping(value = "hbase")
    public String getRow(String rowKey){
        final Result uws_phone_history = hbaseOpsTemplate.getRow("uws_phone_history", rowKey);
        System.out.println(uws_phone_history);
        return "ok";
    }

    @GetMapping(value = "hbase1")
    public R getLists(String rowKey){
        List<HbasePhoneEntity> listByRowKeys = hbaseOpsTemplate.getListByRowKeys(new HbasePhoneEntity(), rowKey);
        return R.ok(listByRowKeys);
    }

    @GetMapping(value = "hbase2")
    public R getCf(String rowKey){
        List<String> listByRowKeys = hbaseOpsTemplate.familyColumns("uws_phone_history");
        return R.ok(listByRowKeys);
    }

    @GetMapping(value = "hbase3")
    public R getScan(String rowKey){
        Map<String,String> map=new HashMap<>();
        map.put("sex",rowKey);
        List<HbasePhoneEntity> hbaseExampleEntities = hbaseOpsTemplate.queryScan(new HbasePhoneEntity(), map);
        return R.ok(hbaseExampleEntities);
    }

    @GetMapping(value = "hbase4")
    public R getScan2(String rowKey){
        Map<String,String> map=new HashMap<>();
        map.put("day",rowKey);
        List<HbaseMacEntity> hbaseExampleEntities = hbaseOpsTemplate.queryScan(new HbaseMacEntity(), map);
        return R.ok(hbaseExampleEntities);
    }

    @GetMapping(value = "hbase5")
    public R getScan3(String startKey,String endKey,String day) throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("day",day);
        Map<String,String> hh=new HashMap<>();
        String tableName="uws_phone_history";
        String cf="cf";
        final List<Result> results = hbaseOpsTemplate.queryScan(tableName, cf, map, hh, startKey, endKey, 2);
        return R.ok(hbaseOpsTemplate.resultToBean(results,new HbasePhoneEntity()));
    }

    @GetMapping(value = "hbase6")
    public R getLists11(String rowKey){
        List<HbaseMacEntity> listByRowKeys = hbaseOpsTemplate.getListByRowKeys(new HbaseMacEntity(), rowKey);
        return R.ok(listByRowKeys);
    }


}

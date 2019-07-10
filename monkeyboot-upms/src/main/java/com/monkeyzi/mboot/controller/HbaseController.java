package com.monkeyzi.mboot.controller;

import com.monkeyzi.common.hbase.entity.HbaseExampleEntity;
import com.monkeyzi.common.hbase.template.HbaseOpsTemplate;
import com.monkeyzi.mboot.common.core.result.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        List<HbaseExampleEntity> listByRowKeys = hbaseOpsTemplate.getListByRowKeys(new HbaseExampleEntity(), rowKey);
        return R.ok(listByRowKeys);
    }

    @GetMapping(value = "hbase2")
    public R getCf(String rowKey){
        List<String> listByRowKeys = hbaseOpsTemplate.familyColumns("uws_phone_history");
        return R.ok(listByRowKeys);
    }
}

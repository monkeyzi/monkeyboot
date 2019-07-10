package com.monkeyzi.common.hbase.config;

import com.monkeyzi.common.hbase.template.HbaseOpsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.Set;
@Slf4j
@EnableConfigurationProperties(HbaseProperties.class)
public class HbaseConnectFactory implements InitializingBean {

    private static Configuration conf = HBaseConfiguration.create();

    public static Connection connection;

    private HbaseProperties hbaseProperties;

    private HbaseConnectFactory(HbaseProperties hbaseProperties){
        this.hbaseProperties=hbaseProperties;
    }


    @Bean
    public HbaseOpsTemplate hbaseOpsTemplate(){
        return new HbaseOpsTemplate();
    }
    public void afterPropertiesSet() throws Exception {
        Map<String, String> config = hbaseProperties.getConfig();
        Set<String> keySet = config.keySet();
        for (String key : keySet) {
            conf.set(key, config.get(key));
        }
        try {
            connection=ConnectionFactory.createConnection(conf);
            log.info("hbase 连接成功");
        }catch (Exception e){
            e.printStackTrace();
            log.error("hbase连接失败");
        }
    }
}

package com.monkeyzi.common.hbase.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "hbase")
public class HbaseProperties {
    private Map<String,String>  config;
}

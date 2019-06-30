package com.monkeyzi.mboot.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全配置
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "mboot.org.springframework.security")
public class SecurityProperties {

    private PermitProperties ignore = new PermitProperties();

}

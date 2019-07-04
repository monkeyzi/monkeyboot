
package com.monkeyzi.mboot.common.security.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源服务器对外直接暴露URL
 */
@Data
@Configuration
@ConditionalOnExpression("!'${security.oauth2.client.ignore-urls}'.isEmpty()")
@ConfigurationProperties(prefix = "security.oauth2.client")
public class PermitAllUrlProperties {
	private List<String> ignoreUrls = new ArrayList<>();
}

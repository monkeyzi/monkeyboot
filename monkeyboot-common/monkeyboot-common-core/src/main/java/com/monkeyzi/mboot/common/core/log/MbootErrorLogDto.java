package com.monkeyzi.mboot.common.core.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author: 高yg
 * @date: 2019/7/6 11:49
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MbootErrorLogDto implements Serializable {
    private Exception e;
    private HttpServletRequest request;
    private String serviceName;
    private String method;
    private String requestUri;
}

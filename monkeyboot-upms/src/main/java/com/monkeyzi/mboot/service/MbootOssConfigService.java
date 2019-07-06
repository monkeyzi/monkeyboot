package com.monkeyzi.mboot.service;

import com.monkeyzi.mboot.common.core.service.ISuperService;
import com.monkeyzi.mboot.entity.MbootFile;
import com.monkeyzi.mboot.entity.MbootOssConfig;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootOssConfigService extends ISuperService<MbootOssConfig> {
    /**
     * 获取当前使用的oss对象
     * @return
     */
    MbootOssConfig getCurrentUsedOss();
}

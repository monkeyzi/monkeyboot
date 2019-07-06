package com.monkeyzi.mboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monkeyzi.mboot.common.core.constant.OssSettingConstants;
import com.monkeyzi.mboot.common.core.exception.BusinessException;
import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.entity.MbootOssConfig;
import com.monkeyzi.mboot.enums.DelStatusEnum;
import com.monkeyzi.mboot.mapper.MbootOssConfigMapper;
import com.monkeyzi.mboot.service.MbootOssConfigService;
import com.monkeyzi.mboot.utils.util.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
public class MbootOssConfigServiceImpl extends SuperServiceImpl<MbootOssConfigMapper,MbootOssConfig> implements MbootOssConfigService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public MbootOssConfig getCurrentUsedOss() {
        //找到redis中配置的当前能用的对象存储
        MbootOssConfig oss= (MbootOssConfig) redisTemplate.opsForValue().get(OssSettingConstants.OSS_USED);
        if (PublicUtil.isEmpty(oss)){
            oss=this.getOne(new QueryWrapper<MbootOssConfig>()
                    .lambda().eq(MbootOssConfig::getIsDel,DelStatusEnum.IS_NOT_DEL.getType())
                    .eq(MbootOssConfig::getStatus,0));
            if (oss==null){
                throw new BusinessException("文件上传失败,没有配置文件存储！");
            }
            redisTemplate.opsForValue().set(OssSettingConstants.OSS_USED,oss);
        }
        return oss;
    }
}

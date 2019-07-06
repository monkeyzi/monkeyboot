package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.monkeyzi.mboot.common.core.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * @author: 高yg
 * @date: 2019/7/6 18:18
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description: 文件存储配置
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "mboot_oss_config")
@Alias(value = "mbootOssConfig")
public class MbootOssConfig  extends BaseEntity {
    @TableId
    private Integer id;

    private String serverName;

    private String serverId;

    private String accessKey;

    private String accessSecret;

    private String bucket;

    private String endPoint;

    private String http;

    @TableLogic
    private Integer isDel;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String createBy;

}

package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.monkeyzi.mboot.common.core.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "mboot_login_log")
@Alias(value = "mbootLoginLog")
public class MbootLoginLog extends BaseEntity {

    @TableId
    private Integer id;

    private String  logName;

    private String  serviceName;

    private String  remoteIp;

    private String  userAgent;

    private String  requestUri;

    private String  params;

    private Integer tenantId;

    @TableLogic
    private Integer isDel;

    private String  requestType;

    private String  os;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime  createTime;

    private String  createBy;

    private Integer createUserId;



}

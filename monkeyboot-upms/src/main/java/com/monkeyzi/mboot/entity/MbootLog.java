package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.monkeyzi.mboot.common.core.model.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "mboot_log")
@Alias(value = "mbootLog")
public class MbootLog  extends BaseEntity {

    @TableId
    private Integer id;

    private String  logName;

    private Integer logType;

    private String  serviceName;

    private String  remoteIp;

    private String  userAgent;

    private String  requestUri;

    private String  method;

    private String  className;

    private String  params;

    private Integer tenantId;

    @TableLogic
    private Integer isDel;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer executeTime;

    private String  requestType;

    private String  os;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime  createTime;

    private String  createBy;

    private Integer createUserId;



}

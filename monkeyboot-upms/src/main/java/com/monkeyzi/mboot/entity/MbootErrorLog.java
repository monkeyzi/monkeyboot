package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.monkeyzi.mboot.common.core.model.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
@Data
@ToString
@Builder
@EqualsAndHashCode
@TableName(value = "mboot_error_log")
@Alias(value = "mbootErrorLog")
public class MbootErrorLog extends BaseEntity {

    private Integer id;

    private String  serviceName;

    private String  exceptionMessage;

    private String  exceptionCause;

    private String  exceptionSimpleName;

    private String  exceptionStack;

    @TableLogic
    private Integer isDel;

    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private Integer createUserId;

    private Integer status;


}

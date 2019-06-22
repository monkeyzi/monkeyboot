package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@EqualsAndHashCode
@TableName(value = "mboot_user_role")
@Alias(value = "mbootUserRole")
public class MbootUserRole  {
    private Integer userId;
    private Integer roleId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    private String createBy;

}

package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@TableName(value = "mboot_role_permission")
@Alias(value = "mbootRolePermission")
public class MbootRolePermission implements Serializable {

    private Integer roleId;

    private Integer permissionId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String createBy;
}

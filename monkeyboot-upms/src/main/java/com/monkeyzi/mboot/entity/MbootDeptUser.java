package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode
@TableName(value = "mboot_dept_user")
@Alias(value = "mbootDeptUser")
@AllArgsConstructor
@NoArgsConstructor
public class MbootDeptUser implements Serializable {

    private Integer deptId;

    private Integer userId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String createBy;
}

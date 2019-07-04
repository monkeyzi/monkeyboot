package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "mboot_user_role")
@Alias(value = "mbootUserRole")
public class MbootUserRole <T extends Model<?>> extends Model<T>{
    private Integer userId;
    private Integer roleId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    private String createBy;

}

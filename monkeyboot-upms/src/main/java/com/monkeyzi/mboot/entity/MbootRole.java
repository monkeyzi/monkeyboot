package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.monkeyzi.mboot.common.core.model.SuperEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@TableName(value = "mboot_role")
@Alias(value = "mbootRole")
@NoArgsConstructor
@AllArgsConstructor
public class MbootRole extends SuperEntity {

    @NotBlank(message = "角色名不能为空")
    private String  roleName;
    @NotBlank(message = "角色编码不能为空")
    private String  roleCode;
    private String  description;
    private String  roleDataScope;
    @TableLogic
    private Integer isDel;
    private Integer tenantId;
    private Integer dataScopeType;


}

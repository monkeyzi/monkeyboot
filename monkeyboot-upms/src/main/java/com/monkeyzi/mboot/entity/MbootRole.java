package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.monkeyzi.mboot.common.core.model.SuperEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Data
@Builder
@EqualsAndHashCode
@TableName(value = "mboot_role")
@Alias(value = "mbootRole")
@NoArgsConstructor
@AllArgsConstructor
public class MbootRole extends SuperEntity {

    private String  roleName;
    private String  roleCode;
    private String  description;
    private String  roleDataScope;
    @TableLogic
    private Integer isDel;
    private Integer tenantId;


}

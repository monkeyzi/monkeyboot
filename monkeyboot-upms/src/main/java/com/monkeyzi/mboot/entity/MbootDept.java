package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.monkeyzi.mboot.common.core.model.SuperEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;
@Data
@Builder
@EqualsAndHashCode
@TableName(value = "mboot_dept")
@Alias(value = "mbootDept")
@NoArgsConstructor
@AllArgsConstructor
public class MbootDept extends SuperEntity {

    private String  deptName;

    private Integer parentId;

    private Integer sort;

    private Integer tenantId;
    @TableLogic
    private Integer isDel;
}

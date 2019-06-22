package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.monkeyzi.mboot.common.core.model.SuperEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@TableName(value = "mboot_permission")
@Alias(value = "mbootPermission")
public class MbootPermission  extends SuperEntity {

    private String  name;
    private String  permission;
    private Integer parentId;
    private String  icon;
    private Integer sort;
    private String  path;
    private String component;
    private Integer type;

    @TableLogic
    private Integer isDel;



}

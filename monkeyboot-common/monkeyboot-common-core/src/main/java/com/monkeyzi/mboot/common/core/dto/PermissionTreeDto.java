package com.monkeyzi.mboot.common.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PermissionTreeDto  extends TreeNode {

    private String  name;
    private String  permission;
    private String  icon;
    private Integer sort;
    private String  path;
    private String  component;
    private Integer type;
    private boolean isCheck=false;

    public PermissionTreeDto() {
    }
}

package com.monkeyzi.mboot.common.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeptTreeDto extends TreeNode {
    private String   deptName;
    private Integer  sort;
}

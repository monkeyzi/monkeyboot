package com.monkeyzi.mboot.common.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTreeDto extends TreeNode {
    private String   deptName;
    private Integer  sort;
}

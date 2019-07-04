package com.monkeyzi.mboot.protocal.req.dept;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: 高yg
 * @date: 2019/7/4 23:30
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
public class DeptSaveReq  implements Serializable {

    @NotNull(message = "父级Id不能为空！")
    private Integer parentId;
    @NotBlank(message = "部门名称不能为空")
    private String deptName;
    @NotNull(message = "排序不能为空")
    private Integer sort;

}

package com.monkeyzi.mboot.protocal.req.permission;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: 高yg
 * @date: 2019/7/4 22:44
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
public class PermissionEditReq implements Serializable {
    @NotNull(message = "节点Id不能为空")
    private Integer id;
    @NotNull(message = "父级不能为空")
    private Integer parentId;
    @NotBlank(message = "名称不能为空")
    private String  name;
    @NotNull(message = "类型不能为空")
    private Integer type;
    @NotNull(message = "排序值不能为空")
    private Integer sort;
    private String  icon;
    /**
     * 地址
     */
    private String  path;
    /**
     * 前端组件
     */
    private String component;


}

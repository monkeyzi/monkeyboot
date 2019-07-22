package com.monkeyzi.mboot.protocal.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/23 19:39
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@ApiModel(value = "新增用户参数")
public class UserSaveReq implements Serializable {
    @ApiModelProperty(value = "用户名",required = true)
    @NotBlank(message = "用户名不能为空")
    private String  username;
    @ApiModelProperty(value = "用户昵称",required = true)
    @NotBlank(message = "用户昵称不能为空")
    private String  nickname;
    @ApiModelProperty(value = "用户密码",required = true)
    @NotBlank(message = "密码不能为空")
    private String  password;
    @ApiModelProperty(value = "用户类型",required = true)
    @NotNull(message = "用户类型不能为空")
    private Integer userType;
    @ApiModelProperty(value = "用户手机号")
    private String  phone;
    @ApiModelProperty(value = "用户邮箱")
    private String  email;
    @ApiModelProperty(value = "部门Id",required = true)
    @NotNull(message = "部门不能为空")
    private Integer deptId;
    @ApiModelProperty(value = "用户状态",required = true)
    @NotNull(message = "用户状态不能为空")
    private Integer status;
    @ApiModelProperty(value = "角色",required = true)
    @NotEmpty(message = "角色不能为空")
    private List<Integer> role;
}

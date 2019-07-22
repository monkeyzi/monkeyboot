package com.monkeyzi.mboot.protocal.req;

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
public class UserEditReq implements Serializable {
    @NotNull(message = "用户Id不能为空")
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    private String  username;
    @NotBlank(message = "用户昵称不能为空")
    private String  nickname;

    private String  password;
    @NotNull(message = "用户类型不能为空")
    private Integer userType;
    private String  phone;
    private String  email;
    @NotNull(message = "部门不能为空")
    private Integer deptId;
    @NotNull(message = "用户状态不能为空")
    private Integer status;
    @NotEmpty(message = "角色不能为空")
    private List<Integer> role;
}

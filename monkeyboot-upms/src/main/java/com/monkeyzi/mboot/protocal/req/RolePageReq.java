package com.monkeyzi.mboot.protocal.req;

import lombok.Data;
import lombok.ToString;

/**
 * 角色列表分页查询入参
 */
@Data
@ToString(callSuper = true)
public class RolePageReq extends BasePageReq {

    private String  startTime;

    private String  endTime;

    private String  roleCode;

    private String  roleName;



}

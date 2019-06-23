package com.monkeyzi.mboot.protocal.req;

import lombok.Data;

/**
 * 角色列表分页查询入参
 */
@Data
public class RolePageReq extends BasePageReq {

    private String  startTime;

    private String  endTime;

    private String  roleCode;

    private String  roleName;



}

package com.monkeyzi.mboot.protocal.req;

import lombok.Data;

/**
 * 用户列表分页查询入参
 */
@Data
public class UserPageReq extends BasePageReq {

    private String  startTime;

    private String  endTime;

    private Integer deptId;

    private String  username;

    private String  phone;

    private Integer userType;

    private Integer status;

}

package com.monkeyzi.mboot.protocal.req;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 用户列表分页查询入参
 */
@Data
@ToString(callSuper = true)
public class UserPageReq extends BasePageReq {

    private String  startTime;

    private String  endTime;

    private List<Integer> deptIds;

    private String  username;

    private String  phone;

    private Integer userType;

    private Integer status;

}

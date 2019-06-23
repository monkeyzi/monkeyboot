package com.monkeyzi.mboot.protocal.resp;

import lombok.Data;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;

/**
 * @author: 高yg
 * @date: 2019/6/23 19:10
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
public class UserInfoVo implements Serializable {

    private Integer userId;
    private String  username;
    private String  nickName;
    private String  headImg;
    /**
     * 权限标识集合
     */
    private String[] permissions;
    /**
     * 角色Id集合
     */
    private Integer[] roleIds;

}

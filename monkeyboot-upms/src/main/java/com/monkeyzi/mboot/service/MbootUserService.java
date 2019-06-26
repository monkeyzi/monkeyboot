package com.monkeyzi.mboot.service;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.common.core.service.ISuperService;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.protocal.req.UserEditReq;
import com.monkeyzi.mboot.protocal.req.UserPageReq;
import com.monkeyzi.mboot.protocal.req.UserSaveReq;
import com.monkeyzi.mboot.protocal.resp.UserInfoVo;
import com.monkeyzi.mboot.security.entity.MbootLoginUser;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootUserService extends ISuperService<MbootUser> {
    /**
     * 分页查询用户信息
     * @param req
     * @return
     */
    PageInfo listPageUserByCondition(UserPageReq req);

    /**
     * 获取当前登录用户的信息
     * @return
     */
    UserInfoVo getCurrentLoginUserInfo();

    /**
     * 根据用户的Id查询用户的基本信息
     * @param id
     * @return
     */
    MbootUser getUserInfoVoById(Integer id);

    /**
     * 根据Id删除用户
     * @param id
     * @return
     */
    boolean deleteUserById(Integer id);

    /**
     * 新增用户
     * @param req
     * @return
     */
    boolean saveUser(UserSaveReq req);

    /**
     * 修改用户
     * @param req
     * @return
     */
    boolean editUser(UserEditReq req);

    /**
     * 查询用户名存在不存在
     * @param username
     * @return
     */
    Integer checkUserName(String username);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    MbootLoginUser getUserByUserName(String username);

    /**
     * 根据手机号查询用户信息
     * @param mobile
     * @return
     */
    MbootLoginUser getUserByMobilePhone(String mobile);

    /**
     * 根据微信openid查询用户的信息
     * @param wxOpenId
     * @return
     */
    MbootLoginUser getUserByWxOpenId(String wxOpenId);
}

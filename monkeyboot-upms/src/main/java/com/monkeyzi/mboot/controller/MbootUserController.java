package com.monkeyzi.mboot.controller;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.annotation.MbootSysLog;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.protocal.req.BasicInfoEditReq;
import com.monkeyzi.mboot.protocal.req.UserEditReq;
import com.monkeyzi.mboot.protocal.req.UserPageReq;
import com.monkeyzi.mboot.protocal.req.UserSaveReq;
import com.monkeyzi.mboot.protocal.resp.UserInfoVo;
import com.monkeyzi.mboot.service.MbootUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author: 高yg
 * @date: 2019/6/19 22:06
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:用户管理
 */
@RestController
@Slf4j
@RequestMapping(value = "/user")
@Validated
@Api(description = "用户管理",value = "user")
public class MbootUserController {

    @Autowired
    private MbootUserService mbootUserService;

    /**
     * 分页查询用户列表
     * @param req
     * @return
     */
    @ApiOperation(value = "用户分页查询")
    @GetMapping(value = "/page")
    @PreAuthorize("@pms.hasPermission('user_page')")
    public R page(@Valid  UserPageReq req){
        log.info("分页查询用户列表的参数为 param={}",req);
        PageInfo pageInfo=mbootUserService.listPageUserByCondition(req);
        return R.ok("ok",pageInfo);
    }

    /**
     * 获取当前登录用户的信息
     * @return
     */
    @MbootSysLog(value = "查询登陆用户的信息")
    @ApiOperation(value = "查询当前登录用户的基本信息")
    @GetMapping(value = "/info")
    public R info(){
        UserInfoVo  userInfo=mbootUserService.getCurrentLoginUserInfo();
        return R.ok("ok",userInfo);
    }

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    @ApiOperation(value = "查询用户的基本信息")
    @GetMapping(value = "/info/{id}")
    public R getUserInfoById(@PathVariable Integer id){
        log.info("根据用户的iD查询用户的基本信息的参数 param={}",id);
        MbootUser mbootUser=mbootUserService.getUserInfoVoById(id);
        return R.ok("ok",mbootUser);
    }

    /**
     * 根据Id删除用户信息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除用户")
    @DeleteMapping(value = "/del/{id}")
    @MbootSysLog(value ="删除用户")
    public R deleteUserById(@PathVariable Integer id){
        log.info("删除用户的参数 param={}",id);
        boolean flag=mbootUserService.deleteUserById(id);
        if (flag){
            return R.okMsg("删除成功！");
        }else {
            return R.errorMsg("删除失败！");
        }
    }

    /**
     * 新增用户
     * @param req
     * @return
     */
    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/save")
    @MbootSysLog(value ="新增用户")
    public R saveUser(@Valid @RequestBody UserSaveReq req){
        log.info("新增用户的参数为 param={}",req);
        boolean flag=mbootUserService.saveUser(req);
        if (flag) {
            return R.okMsg("添加成功！");
        }else {
            return R.errorMsg("新增失败！");
        }
    }

    /**
     * 更新用户
     * @param req
     * @return
     */
    @PutMapping(value = "/edit")
    @ApiOperation(value = "更新用户")
    @MbootSysLog(value ="修改用户")
    public R editUser(@Valid @RequestBody UserEditReq req){
        log.info("修改用户的参数为 param={}",req);
        R flag=mbootUserService.editUser(req);
        return  flag;
    }

    /**
     * 用户自己修改基本信息
     * @param req
     * @return
     */
    @ApiOperation(value = "登录用户修改自己的基本信息")
    @PutMapping(value = "/edit/info")
    @MbootSysLog(value ="登录用户修改自己的基本信息")
    public R editUserInfo(@Valid BasicInfoEditReq req){
        log.info("修改用户的参数为 param={}",req);
        R flag=mbootUserService.editUserInfo(req);
        return  flag;
    }


    /**
     * 登录用户修改密码
     * @param password
     * @param newPassword
     * @return
     */
    @ApiOperation(value = "登录用户修改密码")
    @PutMapping(value = "/edit/pwd")
    @MbootSysLog(value ="登录用户修改密码")
    public R editUserPwd(@NotBlank(message = "原密码不能为空") @RequestParam String password,
                         @NotBlank(message = "新密码不能为空")@RequestParam String newPassword){
        log.info("修改用户的参数为 param1={},param2={}",password,newPassword);
        R flag=mbootUserService.editUserPwd(password,newPassword);
        return  flag;
    }
    /**
     * 查询用户名是否已存在
     * @param username
     * @return
     */
    @ApiOperation(value = "校验用户名是否存在")
    @GetMapping(value = "/check/{username}")
    public R checkUserName(@PathVariable String username){
         log.info("判断用户名是否已存在的参数为 param={}",username);
         Integer count=mbootUserService.checkUserName(username);
         if (count>0){
             return R.errorMsg("用户名已存在");
         }else {
             return R.ok();
         }
    }





}

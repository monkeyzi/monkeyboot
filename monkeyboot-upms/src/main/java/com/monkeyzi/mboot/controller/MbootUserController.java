package com.monkeyzi.mboot.controller;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.protocal.req.UserEditReq;
import com.monkeyzi.mboot.protocal.req.UserPageReq;
import com.monkeyzi.mboot.protocal.req.UserSaveReq;
import com.monkeyzi.mboot.protocal.resp.UserInfoVo;
import com.monkeyzi.mboot.service.MbootUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

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
public class MbootUserController {

    @Autowired
    private MbootUserService mbootUserService;

    /**
     * 分页查询用户列表
     * @param req
     * @return
     */
    @GetMapping(value = "/page")
    public R page(@Valid  UserPageReq req){
        log.info("分页查询用户列表的参数为 param={}",req);
        PageInfo pageInfo=mbootUserService.listPageUserByCondition(req);
        return R.ok("ok",pageInfo);
    }

    /**
     * 获取当前登录用户的信息
     * @return
     */
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
    @DeleteMapping(value = "/{id}")
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
    @PostMapping(value = "/save")
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
     * 新增用户
     * @param req
     * @return
     */
    @PutMapping(value = "/edit")
    public R editUser(@Valid @RequestBody UserEditReq req){
        log.info("修改用户的参数为 param={}",req);
        boolean flag=mbootUserService.editUser(req);
        if (flag) {
            return R.okMsg("修改成功！");
        }else {
            return R.errorMsg("修改失败！");
        }
    }

    /**
     * 查询用户名是否已存在
     * @param username
     * @return
     */
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

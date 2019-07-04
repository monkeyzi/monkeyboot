package com.monkeyzi.mboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monkeyzi.mboot.common.core.dto.DeptTreeDto;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.entity.MbootDept;
import com.monkeyzi.mboot.protocal.req.dept.DeptEditReq;
import com.monkeyzi.mboot.protocal.req.dept.DeptSaveReq;
import com.monkeyzi.mboot.service.MbootDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/23 20:42
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:部门管理
 */
@RestController
@Slf4j
@RequestMapping(value = "/dept")
public class MbootDeptController {
    @Autowired
    private MbootDeptService mbootDeptService;

    /**
     * 部门树查询
     * @return
     */
    @GetMapping(value = "/tree")
    public R  deptTree(){
        List<DeptTreeDto> list=mbootDeptService.deptTree();
        return R.ok("ok",list);
    }

    /**
     * 查询部门详情
     * @param id
     * @return
     */
    @GetMapping(value = "/info/{id}")
    public R deptInfo(@PathVariable Integer id){
        log.info("查询部门详情的参数为 param={}",id);
        MbootDept dept= mbootDeptService.getById(id);
        return R.ok("ok",dept);
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @DeleteMapping(value = "/del/{id}")
    public R removeDeptById(@PathVariable Integer id){
        log.info("删除部门的参数为 param={}",id);
        this.mbootDeptService.removeById(id);
        return R.okMsg("删除成功！");
    }

    /**
     * 新增部门
     * @param req
     * @return
     */
    @PostMapping(value = "/save")
    public R save(@RequestBody @Valid DeptSaveReq req){
        log.info("新增部门的参数为 param={}",req);
        R r=this.mbootDeptService.saveDept(req);
        return r;
    }

    /**
     * 修改部门
     * @param req
     * @return
     */
    @PostMapping(value = "/edit")
    public R edit(@RequestBody @Valid DeptEditReq req){
        log.info("修改部门的参数为 param={}",req);
        R r=this.mbootDeptService.editDept(req);
        return r;
    }
}

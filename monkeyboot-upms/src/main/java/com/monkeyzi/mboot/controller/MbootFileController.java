package com.monkeyzi.mboot.controller;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.protocal.req.file.FilePageReq;
import com.monkeyzi.mboot.service.MbootFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author: 高yg
 * @date: 2019/6/23 20:42
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:文件管理
 */
@RestController
@Slf4j
@RequestMapping(value = "/file")
@Api(description = "文件上传管理",value = "file")
@Validated
public class MbootFileController {

    @Autowired
    private MbootFileService mbootFileService;

    @ApiOperation(value = "文件列表分页查询")
    @GetMapping(value = "/page")
    public R page(@Valid FilePageReq req){
        log.info("分页查询文件的参数为 param={}",req);
        PageInfo pageInfo=mbootFileService.getFilePageByCondition(req);
        return R.ok("ok",pageInfo);
    }


    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/upload")
    public R  upload(@NotNull(message = "文件不能为空") @RequestParam("file") MultipartFile file,
                     @NotNull(message = "文件夹Id不能为空")@RequestParam("folderId")Integer folderId){
        log.info("文件上传的时候文件夹 folderId={}",folderId);
        String result=mbootFileService.uploadFileByFolder(file,folderId);
        return R.ok("上传成功",result);
    }

    @ApiOperation(value = "删除文件")
    @DeleteMapping(value = "/delFile/{id}")
    public R deleteFile(@PathVariable Integer id){
         log.info("删除文件的参数为 fileId={}",id);
         R  r=mbootFileService.deleteFile(id);
         return r;
    }


    @ApiOperation(value = "复制文件")
    @PutMapping(value = "/copyFile/{id}")
    public R copyFile(@PathVariable Integer id){
        log.info("复制文件的参数为 fileId={}",id);
        R  r=mbootFileService.copyFile(id);
        return r;
    }


    @ApiOperation(value = "重命名文件")
    @PutMapping(value = "/reNameFile")
    public R reNameFile(@NotNull(message = "文件Id不能为空")@RequestParam Integer id,
                        @NotNull(message = "新文件名不能为空")@RequestParam String  newName){
        log.info("重命名文件的参数为 fileId={},newName",id,newName);
        R  r=mbootFileService.reNameFile(id,newName);
        return r;
    }
}

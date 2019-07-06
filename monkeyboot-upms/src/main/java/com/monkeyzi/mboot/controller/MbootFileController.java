package com.monkeyzi.mboot.controller;

import com.monkeyzi.mboot.common.core.constant.OssSettingConstants;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.entity.MbootOssConfig;
import com.monkeyzi.mboot.service.MbootFileService;
import com.monkeyzi.mboot.utils.util.PublicUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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



    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/upload")
    public R  upload(@NotNull(message = "文件不能为空") @RequestParam("file") MultipartFile file,
                     @NotNull(message = "文件夹Id不能为空")@RequestParam("folderId")Integer folderId){
        log.info("文件上传的时候文件夹 folderId={}",folderId);
        String result=mbootFileService.uploadFileByFolder(file,folderId);
        return R.ok("上传成功",result);
    }


}

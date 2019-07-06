package com.monkeyzi.mboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monkeyzi.mboot.common.core.constant.OssSettingConstants;
import com.monkeyzi.mboot.common.core.exception.BusinessException;
import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.common.core.utils.FileUtils;
import com.monkeyzi.mboot.common.security.utils.SecurityUtils;
import com.monkeyzi.mboot.entity.MbootFile;
import com.monkeyzi.mboot.entity.MbootFileFolder;
import com.monkeyzi.mboot.entity.MbootOssConfig;
import com.monkeyzi.mboot.entity.MbootRole;
import com.monkeyzi.mboot.enums.DelStatusEnum;
import com.monkeyzi.mboot.mapper.MbootFileMapper;
import com.monkeyzi.mboot.mapper.MbootRoleMapper;
import com.monkeyzi.mboot.service.MbootFileFolderService;
import com.monkeyzi.mboot.service.MbootFileService;
import com.monkeyzi.mboot.service.MbootOssConfigService;
import com.monkeyzi.mboot.service.MbootRoleService;
import com.monkeyzi.mboot.utils.AliOssUtils;
import com.monkeyzi.mboot.utils.util.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
public class MbootFileServiceImpl extends SuperServiceImpl<MbootFileMapper,MbootFile> implements MbootFileService {


    @Autowired
    private MbootOssConfigService mbootOssConfigService;
    @Autowired
    private MbootFileFolderService mbootFileFolderService;



    @Override
    public String  uploadFile(MultipartFile file) {
        //找到redis中配置的当前能用的对象存储
        MbootOssConfig oss= mbootOssConfigService.getCurrentUsedOss();
        String originFileName=file.getOriginalFilename();
        //给文件重命名一下避免因重名被覆盖
        String reFileName=FileUtils.rnFileName(originFileName);
        return upload(file,oss,reFileName);
    }

    @Override
    public String uploadFileByFolder(MultipartFile file, Integer folderId) {
        //查询文件夹信息
        MbootFileFolder folder=mbootFileFolderService.getById(folderId);
        if (folder==null){
            throw new BusinessException("文件上传失败，文件夹不存在！");
        }
        String originFileName=file.getOriginalFilename();
        //给文件重命名一下避免因重名被覆盖
        String reFileName=FileUtils.rnFileName(originFileName);
        MbootOssConfig oss= mbootOssConfigService.getCurrentUsedOss();
        String result=upload(file,oss,reFileName);
        if (StringUtils.isBlank(result)){
            throw new BusinessException("文件上传失败");
        }
        MbootFile mbootFile=new MbootFile();
        mbootFile.setFileOriginName(file.getOriginalFilename());
        mbootFile.setFileSize(String.valueOf(file.getSize()));
        mbootFile.setFileName(reFileName);
        mbootFile.setFileLocation(oss.getServerName());
        mbootFile.setFileUrl(result);
        mbootFile.setIsDel(DelStatusEnum.IS_NOT_DEL.getType());
        mbootFile.setFileType(FileUtils.getFileExtension(originFileName));
        mbootFile.setFolderId(folderId);
        //TODO
        mbootFile.setTenantId(111);
        mbootFile.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        mbootFile.setCreateUserId(SecurityUtils.getLoginUser().getId());
        this.save(mbootFile);
        return result;
    }


    private String upload(MultipartFile file,MbootOssConfig oss,String reFileName){
        //原始名
        String originFileName=file.getOriginalFilename();
        //后缀
        String extName=FileUtils.getFileExtension(originFileName);
        //根据后缀判断存入那个文件夹分类中
        String folder=FileUtils.getFolder(extName);
        String result="";
        try {
            if (oss.getServerId().equals(OssSettingConstants.ALI_OSS)){
                result=AliOssUtils.AliossInputStreamUpload((BufferedInputStream) file.getInputStream(),reFileName,folder,oss);
            }
        }catch (Exception e){
            log.error("文件上传失败 e={}",e);
            return result;
        }
        log.info("文件={}上传成功",originFileName);
        return result;
    }


}

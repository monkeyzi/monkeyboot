package com.monkeyzi.mboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.mboot.common.core.constant.OssSettingConstants;
import com.monkeyzi.mboot.common.core.exception.BusinessException;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.common.core.utils.FileUtils;
import com.monkeyzi.mboot.common.security.utils.SecurityUtils;
import com.monkeyzi.mboot.entity.MbootFile;
import com.monkeyzi.mboot.entity.MbootFileFolder;
import com.monkeyzi.mboot.entity.MbootOssConfig;
import com.monkeyzi.mboot.entity.MbootUser;
import com.monkeyzi.mboot.enums.DelStatusEnum;
import com.monkeyzi.mboot.mapper.MbootFileFolderMapper;
import com.monkeyzi.mboot.mapper.MbootFileMapper;
import com.monkeyzi.mboot.protocal.req.file.FilePageReq;
import com.monkeyzi.mboot.service.MbootFileFolderService;
import com.monkeyzi.mboot.service.MbootFileService;
import com.monkeyzi.mboot.service.MbootOssConfigService;
import com.monkeyzi.mboot.utils.AliOssUtils;
import com.monkeyzi.mboot.utils.util.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.time.LocalDateTime;
import java.util.List;

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
    @Autowired
    private MbootFileMapper mbootFileMapper;



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
        if (oss.getServerId().equals(OssSettingConstants.ALI_OSS)){
            mbootFile.setFileLocation(OssSettingConstants.ALI_OSS);
            mbootFile.setFileLocationName("阿里云OSS");
        }
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

    @Override
    public R deleteFile(Integer id) {
        MbootFile mbootFile=this.getById(id);
        if (mbootFile==null){
            log.warn("文件删除失败 文件不存在id={}",id);
            return R.errorMsg("删除失败，文件不存在！");
        }
        //后缀
        String extName=FileUtils.getFileExtension(mbootFile.getFileOriginName());
        //根据后缀判断存入那个文件夹分类中
        String folder=FileUtils.getFolder(extName);
        //找到存在哪个Oss对象存储中 TODO
        MbootOssConfig ossConfig=mbootOssConfigService.getCurrentUsedOss();
        try {
            AliOssUtils.AliossDeleteFile(folder,mbootFile.getFileName(),ossConfig);
        }catch (Exception e){
            log.error("文件删除异常 e={}",e);
            return R.errorMsg("文件删除失败！");
        }
        mbootFile.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        mbootFile.setUpdateTime(LocalDateTime.now());
        mbootFile.deleteById();
        return R.okMsg("文件删除成功");
    }

    @Override
    public R copyFile(Integer id) {
        MbootFile mbootFile=this.getById(id);
        if (mbootFile==null){
            log.warn("文件删除失败 文件不存在id={}",id);
            return R.errorMsg("删除失败，文件不存在！");
        }
        String newKey="文件副本_"+mbootFile.getFileOriginName();
        String oldKey=mbootFile.getFileName();
        String newFileKey=FileUtils.rnFileName(newKey);
        MbootOssConfig oss=mbootOssConfigService.getCurrentUsedOss();
        String folder=FileUtils.getFolderByOri(newKey);
        String result=null;
        MbootFile mbootFile1=new MbootFile();
        try {
            if (oss.getServerId().equals(OssSettingConstants.ALI_OSS)){
                result=AliOssUtils.AliossCopyFile(oldKey,newFileKey,folder,oss);
                mbootFile1.setFileLocation(OssSettingConstants.ALI_OSS);
                mbootFile1.setFileLocationName("阿里云OSS");
            }
        }catch (Exception e){
            log.error("文件复制异常 e={}",e);
            return R.errorMsg("文件复制失败！");
        }
        mbootFile1.setFileOriginName(newKey);
        mbootFile1.setFileName(newFileKey);
        mbootFile1.setFileType(mbootFile.getFileType());
        mbootFile1.setFileUrl(result);
        mbootFile1.setFileSize(mbootFile.getFileSize());
        mbootFile1.setFolderId(mbootFile.getFolderId());
        mbootFile1.setTenantId(mbootFile.getTenantId());
        mbootFile1.setIsDel(DelStatusEnum.IS_NOT_DEL.getType());
        mbootFile1.setCreateUserId(SecurityUtils.getLoginUser().getId());
        mbootFile1.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        mbootFile1.insert();
        return R.okMsg("文件复制成功");
    }

    @Override
    public R reNameFile(Integer id, String newName) {
        MbootFile mbootFile=this.getById(id);
        if (mbootFile==null){
            log.warn("文件删除失败 文件不存在id={}",id);
            return R.errorMsg("删除失败，文件不存在！");
        }
        mbootFile.setFileOriginName(newName);
        mbootFile.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        this.updateById(mbootFile);
        return R.okMsg("文件重命名成功！");
    }

    @Override
    public PageInfo getFilePageByCondition(FilePageReq req) {
        PageHelper.startPage(req.getPageNum(),req.getPageSize());
        if (PublicUtil.isNotEmpty(req.getEndTime())){
            req.setEndTime(req.getEndTime()+" 23:59:59");
        }
        List<MbootFile> list=this.mbootFileMapper.selectFileByPageAndCondition(req);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
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

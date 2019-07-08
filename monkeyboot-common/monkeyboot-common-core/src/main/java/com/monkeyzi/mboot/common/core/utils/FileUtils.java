package com.monkeyzi.mboot.common.core.utils;

import cn.hutool.core.lang.UUID;
import com.monkeyzi.mboot.common.core.constant.OssSettingConstants;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class FileUtils {

    /**
     * 根据文件的原始名获取到文件的后缀
     * @param originName 文件的原始名称
     * @return
     */
    public  String  getFileExtension(String originName){
        String extName = originName.substring(originName.lastIndexOf(".")+1);
        return extName;
    }

    /**
     * 文件重命名
     * @param originName
     * @return
     */
    public  String  rnFileName(String originName){
        String extName = originName.substring(originName.lastIndexOf("."));
        String reName= UUID.randomUUID().toString()+extName;
        return reName;
    }

    /**
     * 获取文件夹
     * @param fileOriginName
     * @return
     */
    public String  getFolderByOri(String fileOriginName){
        return getFolder(getFileExtension(fileOriginName));
    }

    /**
     * 获取存储的文件夹
     * @param extName  文件后缀
     * @return
     */
    public String getFolder(String extName){
        String folder="";
        if (OssSettingConstants.IMAGE_LIST.contains(extName.toLowerCase())){
            folder=OssSettingConstants.OSS_IMG;
        }else if (OssSettingConstants.FILE_LIST.contains(extName.toLowerCase())){
            folder=OssSettingConstants.OSS_FILE;
        }else if (OssSettingConstants.VIDEO_LIST.contains(extName.toLowerCase())){
            folder=OssSettingConstants.OSS_VIDEO;
        }else if (OssSettingConstants.WORD_LIST.contains(extName.toLowerCase())){
            folder=OssSettingConstants.OSS_WORD;
        }else {
            folder=OssSettingConstants.OSS_OTHER;
        }
        return folder;
    }




}

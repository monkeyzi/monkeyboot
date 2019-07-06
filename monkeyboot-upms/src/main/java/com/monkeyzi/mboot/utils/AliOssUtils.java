package com.monkeyzi.mboot.utils;

import com.aliyun.oss.OSSClient;
import com.monkeyzi.mboot.entity.MbootOssConfig;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 阿里oss工具类
 */
@Slf4j
@UtilityClass
public class AliOssUtils {

    /**
     * 文件流形式上传
     * @param file
     * @param fileName
     * @return
     */
    public String AliossInputStreamUpload(BufferedInputStream file, String fileName, String folder, MbootOssConfig oss){
        OSSClient ossClient=new OSSClient(oss.getHttp()+oss.getEndPoint(),oss.getAccessKey(),oss.getAccessSecret());
        ossClient.putObject(oss.getBucket(),folder+fileName,file);
        ossClient.shutdown();
        return oss.getHttp()+oss.getBucket()+"."+oss.getEndPoint()+"/"+folder+fileName;
    }

    /**
     * 文件路径形式上传
     * @param filePath
     * @param fileName
     * @return
     */
    public String AliossFileUpload(String filePath,String fileName,String folder,MbootOssConfig oss){
        OSSClient ossClient=new OSSClient(oss.getHttp()+oss.getEndPoint(),oss.getAccessKey(),oss.getAccessSecret());
        ossClient.putObject(oss.getBucket(),folder+fileName,new File(filePath));
        ossClient.shutdown();
        return oss.getHttp()+oss.getBucket()+"."+oss.getEndPoint()+"/"+folder+fileName;
    }


    /**
     * 复制文件
     * @param oldKey 源文件名
     * @param newKey 新的文件名
     * @return
     */
    public String AliossCopyFile(String oldKey,String newKey,String folder,MbootOssConfig oss){
        OSSClient ossClient=new OSSClient(oss.getHttp()+oss.getEndPoint(),oss.getAccessKey(),oss.getAccessSecret());
        ossClient.copyObject(oss.getBucket(),folder+oldKey,oss.getBucket(),folder+newKey);
        ossClient.shutdown();
        return oss.getHttp()+oss.getBucket()+"."+oss.getEndPoint()+"/"+folder+newKey;
    }

    /**
     * 重命名文件
     * @param sourceKey 源文件名
     * @param newKey    新文件名
     * @return
     */
    public String AliossRenameFile(String sourceKey,String newKey,String folder,MbootOssConfig oss){
        //先复制
        AliossCopyFile(sourceKey,newKey,folder,oss);
        //删除
        AliossDeleteFile(folder,sourceKey,oss);
        return oss.getHttp()+oss.getBucket()+"."+oss.getEndPoint()+"/"+folder+newKey;
    }

    /**
     * 删除文件
     * @param folder
     * @param key
     */
    public void AliossDeleteFile(String folder,String key,MbootOssConfig oss){
        OSSClient ossClient=new OSSClient(oss.getHttp()+oss.getEndPoint(),oss.getAccessKey(),oss.getAccessSecret());
        ossClient.deleteObject(oss.getBucket(),folder+key);
        ossClient.shutdown();
    }




}

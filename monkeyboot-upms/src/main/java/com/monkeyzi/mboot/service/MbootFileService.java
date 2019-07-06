package com.monkeyzi.mboot.service;

import com.monkeyzi.mboot.common.core.service.ISuperService;
import com.monkeyzi.mboot.entity.MbootDept;
import com.monkeyzi.mboot.entity.MbootFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootFileService extends ISuperService<MbootFile> {
    /**
     * 用户上传-只返回图像地址，不进行数据库操作
     * @param file
     */
    String  uploadFile(MultipartFile file);

    /**
     * 上传文件
     * @param file
     * @param folderId
     * @return
     */
    String  uploadFileByFolder(MultipartFile file,Integer folderId);

}

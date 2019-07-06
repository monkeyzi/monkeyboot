package com.monkeyzi.mboot.common.core.constant;

import java.util.Arrays;
import java.util.List;

/**
 * OSS 常量
 *
 */
public interface OssSettingConstants {

    /**
     * 当前使用OSS
     */
    String OSS_USED = "OSS_CURRENT_USED";

    /**
     * 七牛OSS配置
     */
    String QINIU_OSS = "QINIU_OSS";
    /**
     * 阿里云OSS
     */
    String ALI_OSS = "ALI_OSS";

    /**
     * 存储图片的文件夹
     */
    String OSS_IMG="image/";
    /**
     * 存储视频的文件夹
     */
    String OSS_VIDEO="video/";
    /**
     * 存储其他类型的文件夹 如:压缩文件
     */
    String OSS_FILE="file/";
    /**
     * 存储txt,word,excel等的文件夹
     */
    String OSS_WORD="word/";
    /**
     * 其他类型的文件
     */
    String OSS_OTHER="other/";
    /**
     * 图片
     */
    List<String> IMAGE_LIST = Arrays.asList("jpg","png","jpeg","gif","svg");
    /**
     * 视频类型
     */
    List<String> VIDEO_LIST = Arrays.asList("mpeg","avi","flv","mp4","wmv");

    List<String> FILE_LIST  = Arrays.asList("psd","tar","zip","exe","bat","sh","rar");

    List<String> WORD_LIST  = Arrays.asList("txt","js","css","sass","doc",
            "docx","pdf","xlsx","xls");
}

package com.monkeyzi.mboot.mapper;

import com.monkeyzi.mboot.common.db.mapper.SuperMapper;
import com.monkeyzi.mboot.entity.MbootDept;
import com.monkeyzi.mboot.entity.MbootFile;
import com.monkeyzi.mboot.protocal.req.file.FilePageReq;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:05
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootFileMapper extends SuperMapper<MbootFile> {
    /**
     * 分页查询文件
     * @param req
     * @return
     */
    List<MbootFile> selectFileByPageAndCondition(FilePageReq req);
}

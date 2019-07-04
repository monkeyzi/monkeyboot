package com.monkeyzi.mboot.service;

import com.monkeyzi.mboot.common.core.dto.DeptTreeDto;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.common.core.service.ISuperService;
import com.monkeyzi.mboot.entity.MbootDept;
import com.monkeyzi.mboot.entity.MbootRole;
import com.monkeyzi.mboot.protocal.req.dept.DeptEditReq;
import com.monkeyzi.mboot.protocal.req.dept.DeptSaveReq;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2019/6/19 23:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MbootDeptService extends ISuperService<MbootDept> {
    /**
     * 查询部门树
     * @return
     */
    List<DeptTreeDto> deptTree();

    /**
     * 新增部门
     * @param req
     * @return
     */
    R saveDept(DeptSaveReq req);

    /**
     * 修改部门
     * @param req
     * @return
     */
    R editDept(DeptEditReq req);
}

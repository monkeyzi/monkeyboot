package com.monkeyzi.mboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monkeyzi.mboot.common.core.constant.SecurityConstants;
import com.monkeyzi.mboot.common.core.dto.DeptTreeDto;
import com.monkeyzi.mboot.common.core.result.R;
import com.monkeyzi.mboot.common.core.service.impl.SuperServiceImpl;
import com.monkeyzi.mboot.common.core.utils.TreeUtil;
import com.monkeyzi.mboot.common.security.utils.SecurityUtils;
import com.monkeyzi.mboot.entity.MbootDept;
import com.monkeyzi.mboot.entity.MbootRole;
import com.monkeyzi.mboot.enums.DelStatusEnum;
import com.monkeyzi.mboot.mapper.MbootDeptMapper;
import com.monkeyzi.mboot.mapper.MbootRoleMapper;
import com.monkeyzi.mboot.protocal.req.dept.DeptEditReq;
import com.monkeyzi.mboot.protocal.req.dept.DeptSaveReq;
import com.monkeyzi.mboot.service.MbootDeptService;
import com.monkeyzi.mboot.service.MbootRoleService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class MbootDeptServiceImpl extends SuperServiceImpl<MbootDeptMapper,MbootDept> implements MbootDeptService {
    @Override
    public List<DeptTreeDto> deptTree() {
        List<MbootDept> list=this.list(new QueryWrapper<MbootDept>().lambda()
                .eq(MbootDept::getIsDel,DelStatusEnum.IS_NOT_DEL.getType()));
        ModelMapper modelMapper=new ModelMapper();
        List<DeptTreeDto> dtoList=modelMapper.map(list,new TypeToken<List<MbootDept>>() {}.getType());
        return TreeUtil.build(dtoList,SecurityConstants.DEPT_ROOT);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R saveDept(DeptSaveReq req) {
        ModelMapper modelMapper=new ModelMapper();
        MbootDept dept=new MbootDept();
        modelMapper.map(req,dept);
        dept.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        this.save(dept);
        return R.okMsg("部门新增成功！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R editDept(DeptEditReq req) {
        MbootDept dept=this.getById(req.getId());
        if (dept==null){
            return R.errorMsg("修改失败，部门不存在！");
        }
        ModelMapper modelMapper=new ModelMapper();
        MbootDept mbootDept=new MbootDept();
        modelMapper.map(req,mbootDept);
        mbootDept.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        this.updateById(mbootDept);
        return R.okMsg("部门修改成功！");
    }
}

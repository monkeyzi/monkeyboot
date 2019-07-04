package com.monkeyzi.mboot.service;

import com.monkeyzi.mboot.UpmsApplicationTests;
import com.monkeyzi.mboot.entity.MbootRole;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MbootRoleServiceTest extends UpmsApplicationTests {

    @Autowired
    private MbootRoleService mbootRoleService;

    @Test
    public void test1(){
        List<MbootRole> list=mbootRoleService.list();
        System.out.println(list);
    }


    @Test
    public void test2(){
        MbootRole role=new MbootRole();
        role.setRoleName("测试1");
        role.setRoleCode("ROLE_ADMIN1111");
        role.setRoleDataScope("全部111");
        role.setTenantId(1111111);
        role.setDescription("藐视信息111");
        role.setCreateBy("admin");
        role.setId(5);
        mbootRoleService.save(role);

    }

    @Test
    public void test3(){
        MbootRole role=new MbootRole();
        role.setId(1);
        role.updateById();
    }
}

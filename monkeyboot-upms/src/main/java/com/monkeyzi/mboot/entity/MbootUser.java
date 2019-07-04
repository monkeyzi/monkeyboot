package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.monkeyzi.mboot.common.core.model.SuperEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "mboot_user")
@Alias(value = "mbootUser")
public class MbootUser extends SuperEntity{

    private String  username;
    @JsonIgnore
    private String  password;

    private String  nickname;
    private String  headImg;
    private Integer sex;
    private Integer userType;
    private String  phone;
    private String  email;
    private Integer status;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDel;
    private Integer deptId;
    private String  wxOpenId;
    private String  qqOpenId;
    private Integer tenantId;
    private String  lastLoginIp;
    private LocalDateTime lastLoginTime;
    private String  description;
    private String  browser;
    private String  os;

    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private List<MbootRole> roleList;

    @TableField(exist = false)
    private String[] permissions;

}

package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.monkeyzi.mboot.common.core.model.SuperEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@EqualsAndHashCode
@TableName(value = "mboot_user")
@Alias(value = "mbootUser")
public class MbootUser extends SuperEntity{

    private String  username;
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
    private String  wxOpenId;
    private String  qqOpenId;
    private Integer tenantId;
    private String  lastLoginIp;
    private LocalDateTime lastLoginTime;
    private String description;


}

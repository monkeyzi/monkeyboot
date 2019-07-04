package com.monkeyzi.mboot.protocal.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class BasicInfoEditReq implements Serializable {
    private String  phone;
    private String  email;
    private String  headImg;
    @NotBlank(message = "昵称不能为空")
    private String  nickName;

}

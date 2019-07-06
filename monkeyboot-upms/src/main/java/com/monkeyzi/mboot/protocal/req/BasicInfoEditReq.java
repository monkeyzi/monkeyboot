package com.monkeyzi.mboot.protocal.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class BasicInfoEditReq implements Serializable {
    @NotBlank(message = "用户名不能为空！")
    private String  username;
    private String  phone;
    private String  email;
    private MultipartFile headImg;
    @NotBlank(message = "昵称不能为空")
    private String  nickName;

}

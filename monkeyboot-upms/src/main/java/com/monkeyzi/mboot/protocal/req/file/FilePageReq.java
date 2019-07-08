package com.monkeyzi.mboot.protocal.req.file;

import com.monkeyzi.mboot.protocal.req.BasePageReq;
import lombok.Data;

@Data
public class FilePageReq extends BasePageReq {

    private  Integer folderId;

    private  String  createBy;

    private  String  startTime;

    private  String  endTime;

    private  String  fileName;
}

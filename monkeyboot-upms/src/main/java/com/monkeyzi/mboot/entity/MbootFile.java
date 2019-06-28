package com.monkeyzi.mboot.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.monkeyzi.mboot.common.core.model.SuperEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@TableName(value = "mboot_file")
@Alias(value = "mbootFile")
public class MbootFile extends SuperEntity {

    private String  fileName;

    private String  fileSize;

    private String  fileType;

    private String  fileLocation;

    @TableLogic
    private Integer isDel;

    private String  fileOriginName;

    private String  fileUrl;

    private Integer folderId;

    private Integer tenantId;



}
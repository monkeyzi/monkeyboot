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
@TableName(value = "mboot_file_folder")
@Alias(value = "mbootFileFolder")
public class MbootFileFolder extends SuperEntity {

    private String folderName;

    private String description;

    private String parentId;

    @TableLogic
    private Integer isDel;

    private Integer tenantId;

    private Integer createUserId;


}

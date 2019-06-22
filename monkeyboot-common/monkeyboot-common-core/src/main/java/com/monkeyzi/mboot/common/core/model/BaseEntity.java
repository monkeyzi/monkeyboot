package com.monkeyzi.mboot.common.core.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class BaseEntity  <T extends Model<?>> extends Model<T>  implements Serializable {
}

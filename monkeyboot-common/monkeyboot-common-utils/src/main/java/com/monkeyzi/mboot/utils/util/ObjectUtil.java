package com.monkeyzi.mboot.utils.util;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 高yg
 * @date: 2018/10/15 22:53
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class ObjectUtil {

    public static String mapToString(Map<String, String[]> paramMap){
        return new Gson().toJson(getMap(paramMap));
    }

    public static Map<String,Object> getMap(Map<String, String[]> paramMap){

        if (paramMap == null) {
            return null;
        }
        Map<String, Object> params = new HashMap<>(16);
        paramMap.forEach((k,v)->{
            String value=(v!=null&&v.length>0)?v[0]:"";
            String obj=StrUtil.endWithIgnoreCase(k, "password") ? "*******" : value;
            params.put(k,obj);
        });
        return params;
    }
}

package com.monkeyzi.mboot.utils.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    private ResponseUtil(){

    }

    public static void responseJson(ObjectMapper objectMapper, HttpServletResponse response,
                                    String msg,int httpStatus,Boolean isSuccess)throws IOException {
        Map<String, Object> rsp = new HashMap<>(4);
        response.setStatus(httpStatus);
        rsp.put("code", httpStatus);
        rsp.put("msg", msg);
        rsp.put("success",isSuccess);
        rsp.put("data",null);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (
                Writer writer = response.getWriter()
        ) {
            writer.write(objectMapper.writeValueAsString(rsp));
            writer.flush();
        }
    }
}

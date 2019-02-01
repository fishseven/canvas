package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;

public final class JsonUtil {

    /**
     * 转换对象为json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        try {
            mapper.writeValue(sw, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sw.toString();
    }
}
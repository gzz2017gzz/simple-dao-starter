package com.hq.common.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;

/**
 * @author 高振中
 * @summary 【JSON】转换工具
 * @date 2024-05-10 21:44:15
 **/
public final class JsonTools {

    private JsonTools() {
    }

    public static ObjectMapper mapper;// jackSon工具

    /**
     * Java对象转JSON-String
     */
    @SneakyThrows
    public static String toString(Object obj) {
        return mapper.writeValueAsString(obj);
    }

    /**
     * JSON文本转指定对象
     */
    @SneakyThrows
    public static <T> T toObject(String json, Class<T> clazz) {
        return mapper.readValue(json, clazz);
    }

    /**
     * JSON文本转指定对象List
     */
    @SneakyThrows
    public static <T> List<T> toList(String json, Class<T[]> clazz) {
        return Arrays.asList(mapper.readValue(json, clazz));
    }
}

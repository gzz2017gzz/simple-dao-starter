package com.hq.common.tools;

import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Objects;

import static org.apache.commons.lang3.reflect.FieldUtils.writeField;

/**
 * @author 高振中
 * @summary 属性反射工具类
 * @date 2024-07-28 21:44:15
 **/
public final class FieldTool {
    private FieldTool() {
    }

    /**
     * 获取null值的属性数组
     *
     * @param param 对象
     * @return String[]
     */
    public static String[] nullFields(Object param) {
        return FieldUtils.getAllFieldsList(param.getClass()).stream().filter(i -> Objects.isNull(readField(i, param))).map(Field::getName).toArray(String[]::new);
    }

    /**
     * 读取单项属性值
     */
    @SneakyThrows
    public static Object readField(final Field field, final Object target) {
        return FieldUtils.readField(field, target, true);
    }

    @SneakyThrows
    public static Object readField(final Object target, final String fieldName) {
        return FieldUtils.readField(target, fieldName, true);
    }

    /**
     * 写入单项属性值
     */
    @SneakyThrows
    public static void writeValue(Object object, String name, Object value) {
        writeField(object, name, value, true);
    }
}

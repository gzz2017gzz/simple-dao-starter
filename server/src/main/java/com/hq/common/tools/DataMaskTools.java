package com.hq.common.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @author 吴志建
 * @summary 数据脱敏工具类
 * @date 2024-07-24 21:44:15
 **/

@Slf4j
public final class DataMaskTools {
    private DataMaskTools() {
    } // Cannot be constructed
    /**
     * 脱敏手机号
     *
     * @param dataList  数据
     * @param fieldName 字段名
     */
    public static <T> void doMaskingPhone(List<T> dataList, String fieldName) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }

        for (T item : dataList) {
            try {
                Field field = FieldUtils.getField(item.getClass(), fieldName, true);
                if (Objects.isNull(field)) {
                    continue;
                }
                Object value = field.get(item);
                if (Objects.isNull(value) || !String.class.equals(value.getClass())) {
                    continue;
                }

                field.set(item, value.toString().replaceAll("(\\d{3})\\d{4}(\\d)", "$1****$2"));
            } catch (Exception e) {
                log.error("脱敏手机号异常：", e);
            }
        }
    }

    /**
     * 脱敏身份证号码
     *
     * @param dataList  数据
     * @param fieldName 字段名
     */
    public static <T> void doMaskingIdNumber(List<T> dataList, String fieldName) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }

        for (T item : dataList) {
            try {
                Field field = FieldUtils.getField(item.getClass(), fieldName, true);
                if (Objects.isNull(field)) {
                    continue;
                }
                Object value = field.get(item);
                if (Objects.isNull(value) || !String.class.equals(value.getClass())) {
                    continue;
                }

                if (value.toString().length() > 15) {
                    field.set(item, value.toString().replaceAll("(\\d{6})\\d+(\\d{4})", "$1********$2"));
                } else {
                    field.set(item, value.toString().replaceAll("(\\w{2})\\w+(\\w{2})", "$1******$2"));
                }
            } catch (Exception e) {
                log.error("脱敏手机号异常：", e);
            }
        }
    }
}

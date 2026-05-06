package com.hq.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum EnvEnum {
    UN_KNOW("", ""),
    LOCAL("local", "【本地环境】"),
    DEV("dev", "【开发环境】"),
    TEST("test", "【测试环境】"),
    PROD("prod", "【生产环境】");

    final private String name;
    final private String desc;

    public static EnvEnum parse(String env) {
        for (EnvEnum value : values()) {
            if (value.getName().equals(env)) {
                return value;
            }
        }
        return UN_KNOW;
    }

    /**
     * 是否为生环境
     */
    public static boolean notProd(String[] profiles) {
        return !Arrays.asList(profiles).contains(PROD.getName());
    }
}

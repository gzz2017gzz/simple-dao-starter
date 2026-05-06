package com.hq.common.enums.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * 是否 枚举
 *
 * @author 高振中
 * @date 2024-05-10 21:44:15
 **/
@Getter
@AllArgsConstructor(access = PRIVATE)
public enum YesNo {

    YES(Byte.valueOf("1"), "是"),
    NO(Byte.valueOf("0"), "否");

    private final Byte code;
    private final String name;

    /**
     * 判断YES
     *
     * @param code 值
     * @return boolean
     */
    public static boolean yes(Byte code) {
        return YES.getCode().equals(code);
    }

    /**
     * 判断NO
     *
     * @param code 值
     * @return boolean
     */
    public static boolean no(Byte code) {
        return NO.getCode().equals(code);
    }

    public static String parseName(Byte code) {
        if (yes(code)) {
            return YES.getName();
        }
        if (no(code)) {
            return NO.getName();
        }
        return null;
    }

    public static Byte parseCode(String name) {
        if (YES.getName().equals(name)) {
            return YES.getCode();
        }

        if (NO.getName().equals(name)) {
            return NO.getCode();
        }

        return null;
    }
}

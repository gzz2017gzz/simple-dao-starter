package com.hq.common.enums.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * 用户岗位
 */
@Getter
@AllArgsConstructor(access = PRIVATE)
public enum UserPostType {
    CS_USER(Byte.valueOf("11"), "课程顾问"),
    CS_GROUP_LEADER(Byte.valueOf("13"), "课程组长"),
    CS_MANAGER(Byte.valueOf("12"), "课程主管"),
    SA_ADMIN(Byte.valueOf("15"), "学管"),
    SA_ADMIN_MANAGER(Byte.valueOf("16"), "学管主管"),
    SA_ADMIN_MANAGER_LEADER(Byte.valueOf("27"), "学管负责人"),
    ;

    private final Byte code;
    private final String name;

    /**
     * 课程顾问
     *
     * @return Object[]
     */
    public static boolean isCs(Byte code) {
        return CS_USER.getCode().equals(code) || CS_GROUP_LEADER.getCode().equals(code) || CS_MANAGER.getCode().equals(code);
    }

    /**
     * 学管
     *
     * @return Object[]
     */
    public static Object[] sa() {
        return new Object[]{CS_USER.getCode(), CS_GROUP_LEADER.getCode(), CS_MANAGER.getCode()};
    }
}

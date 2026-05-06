package com.hq.common.enums.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * 用户权限类型
 */
@Getter
@AllArgsConstructor(access = PRIVATE)
public enum UserAuthType {
    PERSON(Byte.valueOf("0"), "个人数据权限"),
    SCHOOL(Byte.valueOf("1"), "校区数据权限"),
    ALL(Byte.valueOf("2"), "全局数据权限"),
    GROUP(Byte.valueOf("3"), "组员数据权限");

    final private Byte code;
    final private String desc;
}
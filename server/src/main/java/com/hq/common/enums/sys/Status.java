package com.hq.common.enums.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author 高振中
 * @summary 【状态】
 * @date 2024-05-10 21:44:15
 **/
@Getter
@AllArgsConstructor(access = PRIVATE)
public enum Status {
    DISABLE(Byte.valueOf("0"), "禁用"),
    ENABLE(Byte.valueOf("1"), "已激活"),
    DISABLED(Byte.valueOf("2"), "已禁用"),
    UN_ENABLE(Byte.valueOf("4"), "未激活"),
    QUIT(Byte.valueOf("5"), "已退出"),
    ;
    private final Byte code;
    private final String name;
}

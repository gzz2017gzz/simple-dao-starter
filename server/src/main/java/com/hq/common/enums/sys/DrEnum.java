package com.hq.common.enums.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * 删除标记 枚举
 *
 * @author 高振中
 * @date 2024-05-10 21:44:15
 **/
@Getter
@AllArgsConstructor(access = PRIVATE)
public enum DrEnum {
    NORMAL(Byte.valueOf("0"), "正常"), DELETED(Byte.valueOf("1"), "已删除");
    private final Byte code;
    private final String name;
}

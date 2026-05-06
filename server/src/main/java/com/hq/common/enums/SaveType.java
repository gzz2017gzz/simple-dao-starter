package com.hq.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * 保存类型
 */
@Getter
@AllArgsConstructor(access = PRIVATE)
public enum SaveType {
    SAVE(Byte.valueOf("1"), "保存"),
    SAVE_COMMIT(Byte.valueOf("2"), "保存并提交");
    private final Byte code;
    private final String desc;
}

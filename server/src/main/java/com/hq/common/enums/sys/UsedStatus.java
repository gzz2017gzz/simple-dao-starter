package com.hq.common.enums.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author 高振中
 * @summary 【使用状态】
 * @date 2024-05-10 21:44:15
 **/
@Getter
@AllArgsConstructor(access = PRIVATE)
public enum UsedStatus {
    //
    UN_USED(Byte.valueOf("0"), "未使用"), USED(Byte.valueOf("1"), "已使用");
    private final Byte code;
    private final String name;
}

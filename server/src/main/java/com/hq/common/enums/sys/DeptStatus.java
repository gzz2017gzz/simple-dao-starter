package com.hq.common.enums.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author 高振中
 * @summary 【菜单/机构状态】
 * @date 2024-05-10 21:44:15
 **/
@Getter
@AllArgsConstructor(access = PRIVATE)
public enum DeptStatus {
    //
    ENABLE(Byte.valueOf("1"), "启用"), DISABLE(Byte.valueOf("0"), "禁用");
    private final Byte code;
    private final String name;
}

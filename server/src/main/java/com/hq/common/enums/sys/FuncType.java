package com.hq.common.enums.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author 高振中
 * @summary 【功能类型】
 * @date 2024-05-10 21:44:15
 **/
@Getter
@AllArgsConstructor(access = PRIVATE)
public enum FuncType {
    //
    MENU(Byte.valueOf("1"), "菜单"), BUTTON(Byte.valueOf("2"), "按钮");
    private final Byte code;
    private final String name;
}

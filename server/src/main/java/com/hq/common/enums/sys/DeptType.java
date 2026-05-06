package com.hq.common.enums.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author 高振中
 * @summary 【机构类型】
 * @date 2024-05-10 21:44:15
 **/
@Getter
@AllArgsConstructor(access = PRIVATE)
public enum DeptType {
    UNIT(Byte.valueOf("1"), "单位"), DEPART(Byte.valueOf("2"), "部门"), SCHOOL(Byte.valueOf("3"), "校区");
    private final Byte code;
    private final String name;
}

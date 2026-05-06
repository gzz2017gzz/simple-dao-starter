package com.hq.common.enums.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author 高振中
 * @summary 【节点类型是否为叶子】
 * @date 2024-05-10 21:44:15
 **/
@Getter
@AllArgsConstructor(access = PRIVATE)
public enum LeafType {
    //
    LEAF(Byte.valueOf("1"), "叶子"), NO_LEAF(Byte.valueOf("0"), "非叶子");
    private final Byte code;
    private final String name;

}

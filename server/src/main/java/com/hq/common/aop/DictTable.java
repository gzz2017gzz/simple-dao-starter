package com.hq.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 高振中
 * @summary 表翻译
 * @date 2024-05-10 21:44:15
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DictTable {
    String table() default ""; //表名

    String id() default ""; //ID字段名

    String name() default ""; //名称字段名

    String field() default ""; //存储值的字段名
}

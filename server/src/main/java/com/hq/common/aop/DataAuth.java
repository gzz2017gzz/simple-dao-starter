package com.hq.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 高振中
 * @summary 【仅个人权限】注解
 * @date 2024-09-24 21:44:15
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataAuth {
    String value() default "t.create_by";// 创建人字段名
}


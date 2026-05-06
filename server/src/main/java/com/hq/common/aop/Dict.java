package com.hq.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 高振中
 * @summary 字典翻译
 * @date 2024-05-10 21:44:15
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {
    String code() default ""; //字典KEY

    String field() default ""; //数据库存字典值的字段名
}

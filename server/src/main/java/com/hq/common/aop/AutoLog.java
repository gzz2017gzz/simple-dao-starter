package com.hq.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 高振中
 * @summary 【日志记录】标记注解
 * @date 2024-05-10 21:44:15
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoLog {
    String value() default "";// 日志标题

    boolean skipResult() default false; //是否跳过记录响应结果
}

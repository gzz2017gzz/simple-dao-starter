package com.hq.common.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.simple.common.base.key.Const.Result.ERROR;
import static com.simple.common.base.key.Const.Result.FAILURE;
import static com.simple.common.base.key.Const.Result.OK;
import static com.simple.common.base.key.Const.Result.SUCCESS;

/**
 * @author 高振中
 * @summary 【反回结果】包装
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@AllArgsConstructor
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 【状态码500-默认失败消息】
     **/
    public static <T> Result<T> error() {
        return new Result<>(ERROR, FAILURE);
    }

    /**
     * 【状态码500-自定义失败消息】
     **/
    public static <T> Result<T> error(String msg) {
        return new Result<>(ERROR, msg);
    }

    /**
     * 【自定义编码-自定义失败消息】
     **/
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg);
    }

    /**
     * 【成功-默认消息-没数据】
     **/
    public static <T> Result<T> success() {
        return new Result<>(OK, SUCCESS);
    }

    /**
     * 【成功-默认消息-带数据】
     **/
    public static <T> Result<T> success(T data) {
        return new Result<>(OK, SUCCESS, data);
    }

    /**
     * 【成功-自定议消息-带数据】
     **/
    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(OK, msg, data);
    }
}

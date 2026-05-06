package com.hq.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.sql.SQLException;

import static com.hq.common.config.Result.error;

/**
 * @author 高振中
 * @summary 【全局异常】处理
 * @date 2024-05-10 21:44:15
 **/
@Slf4j
@ResponseBody
@ControllerAdvice
public class DefaultExceptionAdvice {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSize;// 配置中的单个文件大小

    /**
     * 【参数校验异常】
     **/
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> valid(MethodArgumentNotValidException ex) {
        log.error("参数验证失败：", ex);
        return error("参数验证失败:" + ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList());
    }

    @ExceptionHandler(SQLException.class)
    public Result<String> sql(SQLException ex) {
        log.error(ex.getMessage(), ex);
        return error(ex.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public Result<String> io(IOException ex) {
        log.error(ex.getMessage(), ex);
        return error(ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<String> io(MaxUploadSizeExceededException ex) {
        log.error(ex.getMessage(), ex);
        return error("超过文件最大尺寸," + maxSize + ".");
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<String> runtime(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return error(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> ex(Exception ex) {
        log.error(ex.getMessage(), ex);
        return error(ex.getMessage());
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public Result<String> exceptionHandler(BadSqlGrammarException ex) {
        log.error("数据库错误:", ex);
        return error("数据库错误，请联系信息部");
    }

    @ExceptionHandler(CrmException.class)
    public Result<String> exceptionHandler(CrmException ex) {
        log.error("自定义异常:", ex);
        return error(ex.getMessage());
    }
}

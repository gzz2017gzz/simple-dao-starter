package com.hq.common.aop;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hq.common.tools.DictTools;
import com.simple.common.base.Page;

/**
 * @author 吴志建
 * @summary 字典翻译
 * @date 2024-07-24 21:44:15
 **/
@Aspect
@Component
public class DictParserAop {

    @Around("@annotation(DictParser)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        this.parseDictText(result);
        return result;
    }

    private void parseDictText(Object data) {
        //分页
        if (data instanceof Page<?>) {
            List<?> dataList = ((Page<?>) data).getDataList();
            if (CollectionUtils.isEmpty(dataList)) {
                return;
            }

            DictTools.parseList((List<?>) dataList);
        }

        //列表
        if (data instanceof List<?>) {
            DictTools.parseList((List<?>) data);
        }

        //对象
        if (data != null) {
            DictTools.parseObject(data);
        }
    }
}

package com.hq.common.aop;

import com.simple.common.base.UserIdProvider;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Aspect
@Component
public class CheckAdminAop {
    @Autowired
    private UserIdProvider userIdProvider;
	
    @Before("@annotation(CheckAdmin)")
    public void doBefore(JoinPoint point) {
        Assert.isTrue(!userIdProvider.userId().equals(1000L), "无操作权限");
    }
}

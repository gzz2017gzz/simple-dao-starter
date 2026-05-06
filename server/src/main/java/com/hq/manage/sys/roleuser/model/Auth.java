package com.hq.manage.sys.roleuser.model;

import java.util.List;

import com.hq.manage.sys.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【授权信息】
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
public class Auth {
    private List<User> userList;// 全部用户
    private List<Long> ids;// 已授权用户ID
}

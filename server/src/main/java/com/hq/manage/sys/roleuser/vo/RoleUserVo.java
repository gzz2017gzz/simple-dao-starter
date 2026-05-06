package com.hq.manage.sys.roleuser.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【角色用户关联】值对像
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
public class RoleUserVo {
    private Long userRoleId;//主键
    private Long userId;//用户主键
    private Long roleId;//角色主键
    private String roleName;//角色名
    private String userName;//用户名
    private String deptName;//部门名
}

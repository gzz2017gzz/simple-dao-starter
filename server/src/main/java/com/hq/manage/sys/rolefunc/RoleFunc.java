package com.hq.manage.sys.rolefunc;

import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【角色功能关联】实体
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Table("sys_role_func")
public class RoleFunc {
    // 字段↓
    @Id
    private Long roleFuncId; // 主键
    private Long roleId; // 角色主键
    private Long funcId; // 权限主键
    // 扩展(显示)属性↓
}

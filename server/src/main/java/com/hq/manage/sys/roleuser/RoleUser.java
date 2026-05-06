package com.hq.manage.sys.roleuser;

import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【角色用户关联】实体
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Table("sys_role_user")
@NoArgsConstructor
public class RoleUser {
    // 字段↓
    @Id
    private Long userRoleId; // 主键
    private Long userId; // 用户主键
    private Long roleId; // 角色主键

    // 扩展(显示)属性↓
    public RoleUser(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}

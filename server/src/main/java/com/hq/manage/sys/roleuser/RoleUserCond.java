package com.hq.manage.sys.roleuser;

import com.simple.common.base.BaseCondition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【角色用户关联】查询条件
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleUserCond extends BaseCondition {
    // 默认条件↓
    private String deptName;
    private String name;
    private Long roleId;
    private Long userId;
    private Object[] roleIds;// 主键列表

    /**
     * @方法说明 拼加条件
     **/
    @Override
    protected void addCondition() {
        add("AND d.name LIKE ?", deptName, 3);
        and("name LIKE", name, 3);
        in("role_id", roleIds);
        in("role_id", roleIds);
        and("role_id =", roleId);
        and("user_id =", userId);
    }
}

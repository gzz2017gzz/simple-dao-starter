package com.hq.manage.sys.roledept;

import com.simple.common.base.BaseCondition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【角色部门关联】查询条件
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDeptCond extends BaseCondition {

    // 默认条件↓
    private Long roleId;// 主键列表
    private Object[] roleIds;// 主键列表
    private Long deptId; // 部门id

    /**
     * @方法说明 拼加条件
     **/
    @Override
    protected void addCondition() {
        in("role_id", roleIds);
        and("role_id =", roleId);
        and("dept_id =", deptId);

    }
}

package com.hq.manage.sys.role;

import com.simple.common.base.BaseCondition;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【角色】查询条件
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
public class RoleCond extends BaseCondition {

    // 默认条件↓
    private byte dr;
    private Long roleId; // 主键
    private String name; // 角色名称
    private String remark; // 角色名称
    private Integer roleAuth;//数据权限
    // 自定义条件↓
    private String nameEq; // 角色名称
    private Long roleIdNe; // 主键


    /**
     * @方法说明 拼加条件
     **/
    @Override
    protected void addCondition() {
        and("role_id =", roleId);
        and("role_auth =", roleAuth);
        and("name LIKE", name, 3);
        and("remark LIKE", remark, 3);
        and("dr =", dr);
        and("name =", nameEq);
        and("role_id <>", roleIdNe);
    }

}

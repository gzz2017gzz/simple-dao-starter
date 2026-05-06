package com.hq.manage.sys.rolefunc;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.simple.common.base.BaseDao;

/**
 * @author 高振中
 * @类说明 【角色功能关联】数据访问层
 * @date 2024-05-10 21:44:15
 **/
@Repository
public class RoleFuncDao extends BaseDao<RoleFunc> {
    /**
     * @方法说明 当前用户分配的菜单ids
     */
    public List<Long> funcIds(Long userId) {
        String sql = "SELECT DISTINCT rf.func_id FROM sys_role_user ru JOIN sys_role_func rf ON ru.role_id = rf.role_id WHERE ru.user_id=?";
        return columns(sql, Long.class, userId);
    }

    /**
     * @方法说明 【角色功能关联】列表
     */
    public List<RoleFunc> list(Long roleId) {
        String sql = "SELECT t.role_func_id,t.role_id,t.func_id FROM sys_role_func t JOIN sys_func f ON t.func_id = f.func_id WHERE f.leaf=1 AND t.role_id=?";
        return list(sql, RoleFunc.class, roleId);
    }
}

package com.hq.manage.sys.roleuser;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hq.manage.sys.roleuser.vo.RoleUserVo;
import com.simple.common.base.BaseDao;

/**
 * @author 高振中
 * @类说明 【角色用户关联】数据访问层
 * @date 2024-05-10 21:44:15
 **/
@Repository
public class RoleUserDao extends BaseDao<RoleUser> {

    /**
     * 是否拥有(敏感字段)按钮权限
     */
    public boolean exists(Long userId, String button) {
        String sql = "SELECT COUNT(*) FROM sys_role_user ru,sys_role_func rf,sys_func fc WHERE rf.role_id=ru.role_id AND rf.func_id=fc.func_id AND ru.user_id = ? AND fc.component = ?";
        return count(sql, userId, button) > 0;
    }

    /**
     * @方法说明 【角色用户关联】列表
     */
    public List<RoleUserVo> listRoles(RoleUserCond cond) {
        String SQL = "SELECT t.user_role_id,t.user_id,t.role_id,r.name role_name,u.name user_name,d.name dept_name FROM sys_role_user t " +
                     "JOIN sys_role r ON t.role_id=r.role_id " +
                     "JOIN sys_user u ON t.user_id=u.user_id " +
                     "LEFT JOIN sys_dept d ON u.dept_id=d.id";
        return list(SQL, cond, RoleUserVo.class);
    }
}

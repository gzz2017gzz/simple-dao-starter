package com.hq.manage.sys.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hq.manage.sys.roledept.RoleDeptCond;
import com.hq.manage.sys.roledept.RoleDeptDao;
import com.hq.manage.sys.rolefunc.RoleFuncCond;
import com.hq.manage.sys.rolefunc.RoleFuncDao;
import com.hq.manage.sys.roleuser.RoleUserCond;
import com.hq.manage.sys.roleuser.RoleUserDao;
import com.simple.common.base.Page;

/**
 * @author 高振中
 * @类说明 【角色】业务逻辑层
 * @date 2024-05-10 21:44:15
 **/
@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao; // 注入【角色】数据访问层
    @Autowired
    private RoleFuncDao roleFuncDao; // 注入【角色功能关联】数据访问层
    @Autowired
    private RoleUserDao roleUserDao; // 注入【角色用户关联】数据访问层
    @Autowired
    private RoleDeptDao roleDeptDao; // 注入【角色部门关联】数据访问层

    /**
     * @方法说明 【角色】新增
     */
    public Long save(Role role) {
        return roleDao.save(role).getRoleId();
    }

    /**
     * @方法说明 【角色】删除
     */
    @Transactional(rollbackFor = Exception.class)
    public int delete(Object[] ids) {
        // 删除授权信息
        roleDeptDao.delete(RoleDeptCond.builder().roleIds(ids).build());
        roleFuncDao.delete(RoleFuncCond.builder().roleIds(ids).build());
        roleUserDao.delete(RoleUserCond.builder().roleIds(ids).build());
        return roleDao.delete(ids);
    }

    /**
     * @方法说明 【角色】更新
     */
    public int update(Role role) {
        return roleDao.update(role);
    }

    /**
     * @方法说明 【角色】分页列表
     */
    public Page<Role> page(RoleCond cond) {
        cond.setOrders("order_num");
        return roleDao.page(cond);
    }

    /**
     * @方法说明 【角色】个数
     */
    public boolean exists(RoleCond cond) {
        return roleDao.exists(cond);
    }
 
}

package com.hq.manage.sys.rolefunc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 高振中
 * @类说明 【角色功能关联】业务逻辑层
 * @date 2024-05-10 21:44:15
 **/
@Service
public class RoleFuncService {
    @Autowired
    private RoleFuncDao roleFuncDao; // 注入【角色功能关联】数据访问层

    /**
     * @方法说明 当前用户分配的菜单ids
     */
    public List<Long> funcIds(Long userId) {
        return roleFuncDao.funcIds(userId);
    }

    /**
     * @方法说明 【角色功能关联】列表
     */
    public List<RoleFunc> list(Long roleId) {
        return roleFuncDao.list(roleId);
    }

    /**
     * @方法说明 【角色功能关联】批量插入
     */
    public void insertBatch(List<RoleFunc> list, Long roleId) {
        roleFuncDao.delete(RoleFuncCond.builder().roleId(roleId).build());
        roleFuncDao.saveBatch(list);
    }

    /**
     * @方法说明 【角色功能关联】是否存在
     */
    public boolean exists(RoleFuncCond cond) {
        return roleFuncDao.exists(cond);
    }
}

package com.hq.manage.sys.roledept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 高振中
 * @类说明 【角色部门关联】业务逻辑层
 * @date 2024-05-10 21:44:15
 **/
@Service
public class RoleDeptService {

    @Autowired
    private RoleDeptDao roleDeptDao; // 注入【角色部门关联】数据访问层

    /**
     * @方法说明 【角色部门关联】列表
     */
    public List<RoleDept> list(Long roleId) {
        return roleDeptDao.list(RoleDeptCond.builder().roleId(roleId).build());
    }

    /**
     * @方法说明 【角色部门关联】批量插入
     */
    public void insertBatch(@RequestBody List<RoleDept> list, Long roleId) {
        roleDeptDao.delete(RoleDeptCond.builder().roleId(roleId).build());
        roleDeptDao.saveBatch(list);
    }

    /**
     * @方法说明 【角色部门关联】记录个数
     */
    public boolean exists(RoleDeptCond cond) {
        return roleDeptDao.exists(cond);
    }
}

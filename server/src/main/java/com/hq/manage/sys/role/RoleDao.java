package com.hq.manage.sys.role;

import org.springframework.stereotype.Repository;

import com.simple.common.base.BaseDao;

/**
 * @author 高振中
 * @类说明 【角色】数据访问层
 * @date 2024-05-10 21:44:15
 **/
@Repository
public class RoleDao extends BaseDao<Role> {
    RoleDao() {
        checkRef = true; /*启用删除时外键检查*/
    }
}

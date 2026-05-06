package com.hq.manage.sys.user;

import java.util.List;
import   com.simple.common.base.key.Const;
import org.springframework.stereotype.Repository;

 
import com.hq.common.enums.sys.Status;
import com.simple.common.base.BaseDao;
import com.simple.common.base.Page;

/**
 * @author 高振中
 * @类说明 【用户】数据访问层
 * @date 2024-05-10 21:44:15
 **/
@Repository
public class UserDao extends BaseDao<User> {
    private static final String SQL = """
            SELECT t.user_id,t.dept_id,t.name,t.password,t.login_name,t.qw_id,t.status,t.school_ids,t.posts,t.create_time,t.create_by,t.exclude_school_ids,
            t.update_by,t.update_time,t.remark,t.dr,d.name dept_name,t.manage,t.auth_type,t.proxy_user_id,p.name proxy_name
            FROM sys_user t
            LEFT JOIN sys_user p on t.proxy_user_id=p.user_id
            LEFT JOIN sys_dept d ON t.dept_id=d.id""";

    /**
     * @方法说明 【用户】分页列表
     */
    public Page<User> pageJoin(UserCond cond) {
        return page(SQL, cond, User.class);
    }

    /**
     * @方法说明 【用户】列表
     */
    public List<User> listJoin(UserCond cond) {
        return list(SQL, cond, User.class);
    }

    public List<User> outList(UserCond cond) {
        return list("SELECT t.user_id, t.name FROM sys_user t", cond, User.class);
    }

    public int deleteQwIdsNotIn(Object[] allQwIds) {
        return update(User.builder().status(Status.QUIT.getCode()).build(), UserCond.builder().qwIdNotIn(allQwIds).userIdNe(Const.ADMIN).build());
    }

    public void deleteQwIdsIn(Object[] allQwIds) {
        update(User.builder().status(Status.QUIT.getCode()).build(), UserCond.builder().qwIdIn(allQwIds).userIdNe(Const.ADMIN).build());
    }
 
}

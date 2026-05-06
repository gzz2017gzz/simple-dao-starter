package com.hq.manage.sys.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hq.common.tools.SysTools;
import com.simple.common.base.Page;
import com.simple.common.base.UserIdProvider;

/**
 * @author 高振中
 * @类说明 【用户】业务逻辑层
 * @date 2024-05-10 21:44:15
 **/
@Service
public class UserService {

    @Autowired
    private UserDao userDao; // 注入【用户】数据访问层
    @Autowired
    private UserIdProvider userIdProvider;
    /**
     * @方法说明 【用户】新增
     */
    public Long save(User user) {
        user.setPassword(SysTools.password(user.getPassword()));
        return userDao.save(user).getUserId();
    }

    /**
     * @方法说明 【用户】删除
     */
    public int delete(Object[] ids) {
        return userDao.delete(ids);
    }

    /**
     * @方法说明 【用户】更新
     */
    public int update(User user) {
        return userDao.update(user);
    }

    /**
     * @方法说明 【用户】分页列表
     */
    public Page<User> page(UserCond cond) {
        cond.setOrders("user_id DESC");
        return userDao.pageJoin(cond);
    }

    /**
     * @方法说明 【用户】列表
     */
    public List<User> list(UserCond cond) {
        cond.setOrders("t.user_id DESC");
        return userDao.listJoin(cond);
    }

    public List<User> outList(UserCond cond) {
        cond.setOrders("t.user_id DESC");
        return userDao.outList(cond);
    }

    public User findOne(Long userId) {
        return userDao.listJoin(UserCond.builder().userId(userId).build()).stream().findFirst().orElse(null);
    }

    /**
     * @方法说明 【用户】个数
     */
    public boolean exists(UserCond cond) {
        return userDao.exists(cond);
    }

    /**
     * @方法说明 【用户】修改密码
     */
    public void updatePass(User user) {
        user.setPassword(SysTools.password(user.getPassword()));
        userDao.update(user);
    }

    /**
     * 设置代理人
     *
     * @param proxyUserId 代理人UID
     */
    public void updateProxyUserId(Long proxyUserId) {
        User user = findOne(userIdProvider.userId());
        user.setProxyUserId(proxyUserId);
        userDao.update(user);
    }
}

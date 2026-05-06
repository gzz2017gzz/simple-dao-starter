package com.hq.manage.sys.roleuser;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hq.manage.sys.roleuser.model.Auth;
import com.hq.manage.sys.roleuser.vo.RoleUserVo;
import com.hq.manage.sys.user.User;
import com.hq.manage.sys.user.UserCond;
import com.hq.manage.sys.user.UserDao;

/**
 * @author 高振中
 * @类说明 【角色用户关联】业务逻辑层
 * @date 2024-05-10 21:44:15
 **/
@Service
public class RoleUserService {

    @Autowired
    private RoleUserDao roleUserDao; // 注入【角色用户关联】数据访问层
    @Autowired
    private UserDao userDao; // 注入【用户】数据访问层

    /**
     * @方法说明 【用户列表与授权信息】
     */
    public Auth auth(RoleUserCond cond) {
        List<RoleUser> users = roleUserDao.list(RoleUserCond.builder().roleId(cond.getRoleId()).build());
        List<User> list = userDao.listJoin(UserCond.builder().deptName(cond.getDeptName()).name(cond.getName()).build());
        return Auth.builder().userList(list).ids(users.stream().map(RoleUser::getUserId).toList()).build();
    }

    /**
     * @方法说明 【角色用户关联】批量插入
     */
    public void insertBatch(List<Long> userIds, Long roleId) {
        Set<Long> existsUserSet = roleUserDao.list(RoleUserCond.builder().roleId(roleId).build()).stream().map(RoleUser::getUserId).collect(Collectors.toSet());
        roleUserDao.saveBatch(userIds.stream().filter(v -> !existsUserSet.contains(v)).map(u -> new RoleUser(u, roleId)).toList());
    }

//    /**
//     * 获取当前用户脱敏字段，默认全脱敏
//     *
//     * @return List<String>
//     */
//    public List<String> unSecrecyFields() {
//        Long userId = FieldUtil.userId();
//        String button = "UnSecrecy";
//        if (Objects.isNull(userId) || userId == 0L || Strings.isBlank(button)) {
//            return List.of("phone", "idNumber", "guardPhone", "guardIdNumber");
//        }
//        if (Objects.equals(userId, Const.ADMIN)) {
//            return List.of();
//        }
//        return roleUserDao.exists(userId, button) ? List.of() : List.of("phone", "idNumber", "guardPhone", "guardIdNumber");
//    }

    /**
     * @方法说明 【角色用户关联】批量保存与更新
     */
    public void saveBatch(List<RoleUser> roleUsers) {
        roleUserDao.replaceBatch(roleUsers);
    }

    /**
     * @方法说明 【角色用户关联】列表
     */
    public List<RoleUserVo> listRoles(RoleUserCond cond) {
        return roleUserDao.listRoles(cond);
    }

    /**
     * @方法说明 【角色用户关联】删除
     */
    public int delete(Object[] ids) {
        return roleUserDao.delete(ids);
    }
}

package com.hq.manage.sys.user;

import java.util.Objects;

import com.hq.common.enums.sys.YesNo;
import com.simple.common.base.BaseCondition;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【用户】查询条件
 * @date 2024-05-10 21:44:15	
 **/
@Setter
@Getter
@Builder
public class UserCond extends BaseCondition {

    // 默认条件↓
    private byte dr; /* 删除标记 */
    private Long userId; // 主键
    private Long deptId; // 机构ID
    private String name; // 帐号
    private String password; // 密码
    private String loginName; // 登录名
    private String loginNameLike; // 登录名模糊
    private String qwId; // 企微ID
    private Byte status; // 状态:1启用0禁用
    private Byte authType; // 状态:权限类型
    private String post; // 岗位集
    private Long proxyUserId; // 代理人
    // 自定义条件↓
    private Object[] userIdIn;
    private Long userIdNe; // 主键不等于
    private String nameEq;
    private String deptName;
    private Integer proxyUserIdHas; // 是否有代理人
    private Object[] qwIdNotIn;
    private Byte qwIdNull;
    private Object[] qwIdIn;
    private Object[] qwNameIn;
    private Object[] qwNameNotIn;

    /**
     * @方法说明 拼加条件
     **/
    @Override
    protected void addCondition() {
        and("user_id =", userId);
        and("dept_id =", deptId);
        and("name =", nameEq);
        and("name LIKE", name, 3);
        and("password LIKE", password, 3);
        and("login_name = ", loginName);
        in("name", qwNameIn);
        notIn("name", qwNameNotIn);
        and("qw_id=", qwId);
        in("qw_id", qwIdIn);
        notIn("qw_id", qwIdNotIn);
        and("dr =", dr);
        and("status = ", status);
        and("auth_type = ", authType);
        and("posts REGEXP ", post);
        in("user_id", userIdIn);
        and("user_id !=", userIdNe);
        and("proxy_user_id =", proxyUserId);
        add("AND t.proxy_user_id > 0", Objects.equals(proxyUserIdHas, 1));
        add("AND (t.proxy_user_id IS NULL OR t.proxy_user_id = 0)", Objects.equals(proxyUserIdHas, 0));
        add("AND d.name LIKE ?", deptName, 3);
        add("and qw_id is null", YesNo.yes(qwIdNull));

        and("login_name LIKE", loginNameLike,3);
    }
}

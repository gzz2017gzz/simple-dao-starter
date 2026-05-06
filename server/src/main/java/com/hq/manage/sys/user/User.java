package com.hq.manage.sys.user;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.simple.common.base.annotation.Exclude;
import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【用户】实体
 * @date 2024-05-10 21:45:31
 **/
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_user")
public class User implements Serializable {
    @Serial
    @Exclude
    private static final long serialVersionUID = 1L;
    // 字段↓
    @Id
    private Long userId; // 主键
    private Long deptId; // 机构ID
    private String name; // 姓名
    private String password; // 密码
    private String loginName; // 登录名
    private String qwId; // 企微ID
    private Byte status; // 状态:1启用0禁用
    private Byte authType; // 权限类型:0个人，1校区
    private Long proxyUserId; // 代理人
    private String posts; // 岗位集
    private LocalDateTime createTime; /* 创建时间 */
    private LocalDateTime updateTime; /* 修改时间 */
    private Byte dr;
    private Long createBy; /* 创建时间 */
    private Long updateBy; /* 创建时间 */
    private String remark; // 备注
    // 扩展(显示)属性↓
    @Exclude
    private String deptName; // 机构名称
    @Exclude
    private String proxyName; // 代理人名称
}

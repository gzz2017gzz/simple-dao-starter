package com.hq.manage.sys.roledept;

import java.time.LocalDateTime;

import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【角色部门关联】实体
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Table("sys_role_dept")
public class RoleDept {
    // 字段↓
    @Id
    private Long roleDeptId; // id
    private Long roleId; // 角色id
    private Long deptId; // 部门id
    private LocalDateTime createTime; // 创建时间
    private Long createBy; // 创建人
    // 扩展(显示)属性↓

}

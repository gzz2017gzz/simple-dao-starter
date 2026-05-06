package com.hq.manage.sys.role;

import java.time.LocalDateTime;

import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【角色】实体
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Table("sys_role")
public class Role {
    // 字段↓
    @Id
    private Long roleId; // 主键
    private String name; // 角色名称
    private String remark; // 备注
    private Integer roleAuth;//数据权限
    private Integer orderNum; // 排序
    private LocalDateTime createTime; // 创建时间
    private Long createBy; // 创建时间
    private LocalDateTime updateTime; // 创建时间
    private Long updateBy; // 创建时间
    private Byte dr; // 创建时间
    // 扩展(显示)属性↓
}

package com.hq.manage.sys.roledept.model;

import com.hq.manage.sys.dept.Dept;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 数据权限值对象
 *
 * @author 高振中
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
public class RoleDeptVO {
    private List<Dept> depts;// 完整部门树
    private List<Long> ids; // 已选中的部门主键
}

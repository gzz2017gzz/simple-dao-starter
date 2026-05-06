package com.hq.manage.sys.rolefunc.model;

import com.hq.manage.sys.func.Func;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 功能权限值对象
 *
 * @author 高振中
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
public class RoleFuncVO {
    private List<Func> funcs;// 功能菜单树
    private List<Long> ids; // 角色主键
}

package com.hq.manage.sys.role;

import static com.hq.common.config.Result.error;
import static com.hq.common.config.Result.success;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hq.common.aop.AutoLog;
import com.hq.common.aop.CheckAdmin;
import com.hq.common.config.Result;
import com.simple.common.base.Page;

/**
 * @author 高振中
 * @类说明 【角色】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService; // 注入【角色】业务逻辑层

    @PostMapping("save")
    @AutoLog("【角色】新增")
    @CheckAdmin
    public Result<Long> save(@RequestBody @Validated Role role) {
        if (roleService.exists(RoleCond.builder().nameEq(role.getName()).build())) {
            return error("【角色名称】不能重复！");
        }
        return success(roleService.save(role));
    }

    @PostMapping("delete")
    @AutoLog("【角色】删除")
    @CheckAdmin
    public Result<Integer> delete(@RequestBody Long[] ids) {
        return success(roleService.delete(ids));
    }

    @PostMapping("update")
    @AutoLog("【角色】修改")
    @CheckAdmin
    public Result<Integer> update(@RequestBody Role role) {
        if (roleService.exists(RoleCond.builder().nameEq(role.getName()).roleIdNe(role.getRoleId()).build())) {
            return error("【角色名称】不能重复！");
        }
        return success(roleService.update(role));
    }

    @PostMapping("updateAuth")
    @AutoLog("【角色】修改数据权限")
    @CheckAdmin
    public Result<Integer> updateAuth(@RequestBody Role role) {
        return success(roleService.update(role));
    }

    @AutoLog("【角色】分页列表")
    @PostMapping("page")
    public Result<Page<Role>> page(@RequestBody RoleCond cond) {
        return success(roleService.page(cond));
    }
 
}

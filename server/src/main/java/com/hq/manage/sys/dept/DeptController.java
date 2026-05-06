package com.hq.manage.sys.dept;

import com.hq.common.aop.AutoLog;
import com.hq.common.aop.CheckAdmin;
import com.hq.common.config.Result;
import com.hq.manage.sys.roledept.RoleDeptCond;
import com.hq.manage.sys.roledept.RoleDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hq.common.config.Result.error;
import static com.hq.common.config.Result.success;
import static com.hq.common.enums.sys.DeptStatus.DISABLE;
import static com.hq.common.enums.sys.DeptType.DEPART;
import static com.hq.common.enums.sys.DeptType.UNIT;

/**
 * @author 高振中
 * @类说明 【组织机构】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    private DeptService deptService; // 注入【组织机构】业务逻辑层
    @Autowired
    private RoleDeptService roleDeptService; // 注入【角色部门关联】业务逻辑层

    @AutoLog("【组织机构】树")
    @PostMapping("tree")
    public Result<List<Dept>> tree(@RequestBody DeptCond cond) {
        return success(deptService.tree(cond));
    }

    @AutoLog("【组织机构】新增")
    @PostMapping("save")
    @CheckAdmin
    public Result<Long> save(@RequestBody @Validated Dept dept) {
        if (!dept.getParentId().equals(0L)) {
            Dept parent = deptService.findById(dept.getParentId());
            if (parent.getType().equals(DEPART.getCode()) && dept.getType().equals(UNIT.getCode())) {
                return error("【部门】下不能建【单位】");
            }
        }
        if (deptService.exists(DeptCond.builder().nameEq(dept.getName()).build())) {
            return error("【组织机构名称】不能重复！");
        }
        return success(deptService.save(dept));
    }

    @AutoLog("【组织机构】删除")
    @PostMapping("delete")
    @CheckAdmin
    public Result<Integer> delete(@RequestBody Dept dept) {
        if (deptService.exists(DeptCond.builder().parentId(dept.getId()).build())) {
            return error("【有下级】不能删除！");
        }
        deptService.delete(dept);
        return success();
    }

    @AutoLog("【组织机构】修改")
    @PostMapping("update")
    @CheckAdmin
    public Result<Integer> update(@RequestBody @Validated Dept dept) {
        if (!dept.getParentId().equals(0L)) {
            Dept parent = deptService.findById(dept.getParentId());
            if (parent.getType().equals(DEPART.getCode()) && dept.getType().equals(UNIT.getCode())) {
                return error("【部门】下不能建【单位】");
            }
        }
        if (deptService.exists(DeptCond.builder().nameEq(dept.getName()).idNe(dept.getId()).build()))
            return error("【组织机构名称】不能重复！");
        if (dept.getStatus().equals(DISABLE.getCode()) && roleDeptService.exists(RoleDeptCond.builder().deptId(dept.getId()).build())) {
            return error("正在使用的【组织机构】不能停用！");
        }
        return success(deptService.update(dept));
    }

    @AutoLog("【组织机构】列表")
    @PostMapping("list")
    public Result<List<Dept>> list(@RequestBody DeptCond cond) {
        return success(deptService.list(cond));
    }
}

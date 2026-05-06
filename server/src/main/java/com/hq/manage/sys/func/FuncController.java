package com.hq.manage.sys.func;

import com.hq.common.aop.AutoLog;
import com.hq.common.aop.CheckAdmin;
import com.hq.common.config.Result;
import com.hq.manage.sys.rolefunc.RoleFuncCond;
import com.hq.manage.sys.rolefunc.RoleFuncService;
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

/**
 * @author 高振中
 * @类说明 【功能菜单】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("func")
public class FuncController {

    @Autowired
    private FuncService funcService; // 注入【功能菜单】业务逻辑层
    @Autowired
    private RoleFuncService roleFuncService;// 注入 角色功能关联 业务逻辑层

    @AutoLog("【功能菜单】新增")
    @PostMapping("save")
    @CheckAdmin
    public Result<Long> save(@RequestBody @Validated Func func) {
        if (!func.getParentId().equals(0L)) {
            Func parent = funcService.findOne(func.getParentId());
            if (!parent.getType().equals(func.getType())) {
                return error("上下级的节点类型要相同!");
            }
        }
        return success(funcService.save(func));
    }

    @AutoLog("【功能菜单】删除")
    @PostMapping("delete")
    @CheckAdmin
    public Result<Integer> delete(@RequestBody Func func) {
        if (funcService.exists(FuncCond.builder().parentId(func.getFuncId()).build())) {
            return error("【有下级】不能删除！");
        }
        funcService.delete(func);
        return success();
    }

    @AutoLog("【功能菜单】修改")
    @PostMapping("update")
    @CheckAdmin
    public Result<Integer> update(@RequestBody @Validated Func func) {
        if (!func.getParentId().equals(0L)) {
            Func parent = funcService.findOne(func.getParentId());
            if (!parent.getType().equals(func.getType())) {
                return error("上下级的节点类型要相同!");
            }
        }
        if (func.getStatus().equals(DISABLE.getCode()) && roleFuncService.exists(RoleFuncCond.builder().funcId(func.getFuncId()).build())) {
            return error("正在使用的【功能菜单】不能停用！");
        }
        return success(funcService.update(func));
    }

    @PostMapping("list")
    @AutoLog("【功能菜单】列表")
    public Result<List<Func>> list(@RequestBody FuncCond cond) {
        return success(funcService.list(cond));
    }

    @PostMapping("tree")
    @AutoLog("【功能菜单】树")
    public Result<List<Func>> tree() {
        return success(funcService.tree(FuncCond.builder().build()));
    }

}

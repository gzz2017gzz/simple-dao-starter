package com.hq.manage.sys.rolefunc;

import com.hq.common.aop.AutoLog;
import com.hq.common.aop.CheckAdmin;
import com.hq.common.config.Result;
import com.hq.manage.sys.func.FuncCond;
import com.hq.manage.sys.func.FuncService;
import com.hq.manage.sys.rolefunc.model.RoleFuncVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hq.common.config.Result.success;

/**
 * @author 高振中
 * @类说明 【角色功能关联】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("roleFunc")
public class RoleFuncController {
    @Autowired
    private RoleFuncService roleFuncService; // 注入【角色功能关联】业务逻辑层
    @Autowired
    private FuncService funcService; // 注入【功能菜单】业务逻辑层

    @AutoLog("【角色功能关联】-查询【功能菜单】树及选中节点")
    @PostMapping("tree")
    public Result<RoleFuncVO> tree(Long roleId) {
        List<Long> ids = roleFuncService.list(roleId).stream().map(RoleFunc::getFuncId).toList();
        return success(RoleFuncVO.builder().ids(ids).funcs(funcService.tree(FuncCond.builder().status((byte) 1).build())).build());
    }

    @AutoLog("【角色功能关联】-保存")
    @PostMapping("save")
    @CheckAdmin
    public Result<Void> insertBatch(@RequestBody List<RoleFunc> list, Long roleId) {
        roleFuncService.insertBatch(list, roleId);
        return success();
    }
}

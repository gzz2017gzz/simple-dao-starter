package com.hq.manage.sys.roleuser;

import com.hq.common.aop.AutoLog;
import com.hq.common.aop.CheckAdmin;
import com.hq.common.config.Result;
import com.hq.manage.sys.roleuser.model.Auth;
import com.hq.manage.sys.roleuser.vo.RoleUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hq.common.config.Result.success;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.springframework.util.Assert.isTrue;

/**
 * @author 高振中
 * @类说明 【角色用户关联】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("roleUser")
public class RoleUserController {

    @Autowired
    private RoleUserService roleUserService; // 注入【角色用户关联】业务逻辑层

    @AutoLog("【角色用户关联】列表")
    @PostMapping("list")
    public Result<Auth> list(@RequestBody RoleUserCond cond) {
        return success(roleUserService.auth(cond));
    }

    @AutoLog("【角色用户关联】批量保存")
    @PostMapping("save")
    @CheckAdmin
    public Result<Void> insertBatch(@RequestBody List<Long> userIds, Long roleId) {
        roleUserService.insertBatch(userIds, roleId);
        return success();
    }

    @AutoLog("【关联用户】")
    @PostMapping("saveBatch")
    @CheckAdmin
    public Result<Void> saveBatch(@RequestBody List<RoleUser> roleUsers) {
        String msg = "角色不能重复!";
        isTrue(roleUsers.stream().collect(groupingBy(RoleUser::getRoleId, counting())).values().stream().noneMatch(v -> v > 1), msg);
        roleUserService.saveBatch(roleUsers);
        return success();
    }
    @AutoLog("【角色列表】")
	@PostMapping("listRoles")
	public Result<List<RoleUserVo>> listRoles(@RequestBody RoleUserCond cond) {
		return success(roleUserService.listRoles(cond));
	}

    @AutoLog("【角色用户关联】删除")
    @PostMapping("delete")
    @CheckAdmin
    public Result<Integer> delete(@RequestBody Long[] ids) {
        return success(roleUserService.delete(ids));
    }
}

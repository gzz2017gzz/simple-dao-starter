package com.hq.manage.sys.user;

import static com.hq.common.config.Result.error;
import static com.hq.common.config.Result.success;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hq.common.aop.AutoLog;
import com.hq.common.aop.CheckAdmin;
import com.hq.common.config.Result;
import com.simple.common.base.Page;

import io.swagger.v3.oas.annotations.Operation;

/**
 * @author 高振中
 * @类说明 【用户】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService; // 注入【用户】业务逻辑层

	@PostMapping("save")
	@AutoLog("【用户】新增")
	@CheckAdmin
	public Result<Long> save(@RequestBody @Validated User user) {
		if (userService.exists(UserCond.builder().loginName(user.getLoginName()).build())) {
			return error("【登录名】不能重复！");
		}
		return success(userService.save(user));
	}

	@AutoLog("【用户】删除")
	@PostMapping("delete")
	@CheckAdmin
	public Result<Integer> delete(@RequestBody Long[] ids) {
		return success(userService.delete(ids));
	}

	@AutoLog("【用户】修改")
	@PostMapping("update")
	@CheckAdmin
	public Result<Integer> update(@RequestBody @Validated User user) {
		if (userService.exists(UserCond.builder().loginName(user.getLoginName()).userIdNe(user.getUserId()).build())) {
			return error("【登录名】不能重复！");
		}
		return success(userService.update(user));
	}

	@AutoLog("【用户】分页列表")
	@PostMapping("page")
	public Result<Page<User>> page(@RequestBody UserCond cond) {
		return success(userService.page(cond));
	}

	@PostMapping("list")
	@Operation(summary = "【用户】列表", description = "【用户】列表")
	@AutoLog("【用户】列表")
	public Result<List<User>> list(@RequestBody UserCond cond) {
		return success(userService.list(cond));
	}

	@PostMapping("out/list")
	@Operation(summary = "【用户】列表", description = "【用户】列表")
	@AutoLog("【用户】列表")
	public Result<List<User>> outList(@RequestBody UserCond cond) {
		return success(userService.outList(cond));
	}

	@AutoLog("【用户】查看")
	@PostMapping("view")
	public Result<User> view(@RequestParam(name = "userId") Long userId) {
		return success(userService.findOne(userId));
	}

	@AutoLog("【用户】修改密码")
	@PostMapping("updatePass")
	public Result<Void> updatePass(@RequestBody User user) {
		userService.updatePass(user);
		return success();
	}

	@AutoLog("【用户】设置代理人")
	@PostMapping("setProxyUser")
	public Result<Void> setProxyUser(@RequestParam(name = "proxyUserId", required = false, defaultValue = "0") Long proxyUserId) {
		userService.updateProxyUserId(proxyUserId);
		return success();
	}
}

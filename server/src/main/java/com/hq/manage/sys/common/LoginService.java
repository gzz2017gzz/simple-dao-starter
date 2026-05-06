package com.hq.manage.sys.common;

import static com.hq.common.Const.Jwt.EXPIRE;
import static com.hq.common.config.Result.error;
import static com.hq.common.config.Result.success;
import static com.hq.common.enums.sys.DeptStatus.ENABLE;
import static com.hq.common.enums.sys.FuncType.BUTTON;
import static com.hq.common.enums.sys.FuncType.MENU;
import static com.simple.common.base.key.Const.ADMIN;
import static com.simple.common.base.key.Const.INT_0;
import static com.simple.common.base.key.Const.INT_3;
import static com.simple.common.base.key.Const.LONG_0;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.hq.common.config.Result;
import com.hq.common.filter.JwtTools;
import com.hq.manage.sys.dictitem.DictItemService;
import com.hq.manage.sys.func.Func;
import com.hq.manage.sys.func.FuncCond;
import com.hq.manage.sys.func.FuncService;
import com.hq.manage.sys.rolefunc.RoleFuncService;
import com.hq.manage.sys.user.User;
import com.hq.manage.sys.user.vo.LoginVO;
import com.simple.common.base.UserIdProvider;

/**
 * @author 张可可
 * @类说明 用户登录 业务逻辑层
 * @date 2024-05-10 21:44:15
 **/

@Service
public class LoginService {
    @Autowired
    private UserIdProvider userIdProvider;
	@Autowired
	private DictItemService dictItemService; // 注入【字典项】业务逻辑层
	@Autowired
	private RedisTemplate<Long, LoginVO> redisTemplate;// 用于存放用户登录信息的redis 模板类
	@Autowired
	private RoleFuncService roleFuncService; // 注入【角色功能关联】业务逻辑层
	@Autowired
	private FuncService funcService; // 注入【功能菜单】业务逻辑层

	public User loginUser() {
		Long userId = userIdProvider.userId();
		return userId.equals(LONG_0) ? new User() : requireNonNull(redisTemplate.opsForValue().get(userId)).getUser();
	}

	/**
	 * 用户账号密码登录
	 */
	public Result<LoginVO> login(User user) {
		Long userId = user.getUserId();
		Object[] funcIds = userId.equals(ADMIN) ? null : roleFuncService.funcIds(userId).toArray();
		if (funcIds != null && funcIds.length == INT_0) {
			return error(INT_3, "用户没有授权菜单,请联系管理员!");
		}

		String token = JwtTools.createToken(user.getUserId());// 生成token
		FuncCond cond = FuncCond.builder().ids(funcIds).status(ENABLE.getCode()).type(MENU.getCode()).build();
		FuncCond button = FuncCond.builder().ids(funcIds).status(ENABLE.getCode()).type(BUTTON.getCode()).build();
		List<Func> menus = funcService.tree(cond);// 查菜单
		List<Func> buttons = funcService.list(button);// 查按钮
 
		LoginVO vo = LoginVO.builder().user(user).token(token).dicts(dictItemService.dict()).menus(menus).buttons(buttons).build();
		LoginVO redisVo = LoginVO.builder().user(user).build();
		redisTemplate.opsForValue().set(user.getUserId(), redisVo, EXPIRE, TimeUnit.DAYS); // 放入缓存
		return success(vo);
	}
}

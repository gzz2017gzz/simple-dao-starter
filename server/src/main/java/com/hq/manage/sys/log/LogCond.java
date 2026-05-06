package com.hq.manage.sys.log;

import com.simple.common.base.BaseCondition;

import lombok.Getter;
import lombok.Setter;

/**
 * @类说明 【系统日志】查询条件
 * @author 高振中
 * @date 2023-11-04 09:57:59
 **/
@Setter
@Getter
public class LogCond extends BaseCondition {

	/**
	 * @方法说明 拼加条件
	 **/
	@Override
	protected void addCondition() {
		and("id =", id);
		and("title LIKE", title, 3);
		and("user_id LIKE", userId, 3);
		and("user_name LIKE ", userName, 3);
		and("ip LIKE", ip, 3);
		and("method LIKE", method, 3);
		and("url LIKE", url, 3);
		and("request_type LIKE", requestType, 3);
		and("params LIKE", params, 3);
		in("id", ids);
	}

	// 默认条件↓
	private Long id; // 主键
	private String title; // 日志标题
	private String userId; // 用户主键
	private String userName; // 用户名
	private String ip; // IP地址
	private String method; // java类.方法
	private String url; // 请求路径
	private String requestType; // 请求类型
	private String params; // 请求参数
	private Object[] ids;// 主键列表
	// 自定义条件↓
}

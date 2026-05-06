package com.hq.manage.sys.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hq.common.config.Result;
import com.simple.common.base.Page;

/**
 * @类说明 【系统日志】控制器
 * @author 高振中
 * @date 2023-11-04 09:57:59
 **/
@RestController
@RequestMapping("log")
public class LogController {

	@Autowired
	private LogService logService; // 注入【系统日志】业务逻辑层

	/**
	 * @方法说明 【系统日志】删除
	 */
	@PostMapping("delete")
	public Result<Integer> delete(@RequestBody Long[] ids) {
		return Result.success(logService.delete(ids));
	}

	/**
	 * @方法说明 【系统日志】分页列表
	 */
	@PostMapping("page")
	public Result<Page<Log>> page(@RequestBody LogCond cond) {
		return Result.success(logService.page(cond));
	}

}

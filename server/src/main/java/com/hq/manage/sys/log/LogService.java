package com.hq.manage.sys.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.common.base.Page;

/**
 * @类说明 【系统日志】业务逻辑层
 * @author 高振中
 * @date 2023-11-04 09:57:59
 **/
@Service
public class LogService {

	@Autowired
	private LogDao logDao; // 注入【系统日志】数据访问层

	/**
	 * @方法说明 【系统日志】删除
	 */
	public int delete(Object[] ids) {
		return logDao.delete(ids);
	}

	/**
	 * @方法说明 【系统日志】分页列表
	 */
	public Page<Log> page(LogCond cond) {
		cond.setOrders("operate_time DESC");
		return logDao.page(cond);
	}

}

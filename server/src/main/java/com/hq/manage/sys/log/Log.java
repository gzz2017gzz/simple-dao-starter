package com.hq.manage.sys.log;

import java.time.LocalDateTime;

import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @类说明 【系统日志】实体
 * @author 高振中
 * @date 2023-11-04 09:57:59
 **/
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_log")
public class Log {
	// 字段↓
	@Id
	private Long id; // 主键
	private String title; // 日志标题
	private Long userId; // 用户主键
	private String userName; // 用户名
	private String ip; // IP地址
	private String method; // java类.方法
	private String url; // 请求路径
	private String requestType; // 请求类型
	private String params; // 请求参数
	private LocalDateTime operateTime;
	private String result;
	// 扩展(显示)属性↓
}

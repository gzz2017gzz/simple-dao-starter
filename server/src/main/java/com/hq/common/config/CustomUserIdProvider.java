package com.hq.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.simple.common.base.UserIdProvider;

import jakarta.servlet.http.HttpSession;

import static com.hq.common.Const.Jwt.USER_ID;

/**
 * 自定义用户ID提供者，演示如何覆盖 SimpleDAO 默认的审计字段来源
 */
@Component
public class CustomUserIdProvider implements UserIdProvider {
	@Override
	public Long userId() {
		HttpSession session = session();
		if (session == null)
			return 0L;
		Object userIdObj = session.getAttribute(USER_ID);
		return (userIdObj instanceof Long) ? (long) userIdObj : 0;
	}

	public static HttpSession session() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attributes == null ? null : attributes.getRequest().getSession(false);
	}

}

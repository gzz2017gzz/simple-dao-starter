package com.hq.common.filter;

import static com.hq.common.Const.CODE;
import static com.hq.common.Const.MESSAGE01;
import static com.hq.common.Const.MESSAGE02;
import static com.hq.common.Const.MESSAGE03;
import static com.hq.common.config.Result.error;
import static com.hq.common.filter.JwtTools.userId;
import static com.hq.common.filter.JwtTools.verify;
import static com.hq.common.Const.Jwt.TOKEN;
import static com.hq.common.Const.Jwt.USER_ID;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hq.common.tools.JsonTools;
import com.hq.manage.sys.user.User;
import com.hq.manage.sys.user.vo.LoginVO;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


/**
 * @author 高振中
 * @summary 【验证令牌】过滤器
 * @date 2024-05-10 21:44:15
 **/
@Slf4j
@Component
public class ValidateTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate<Long, LoginVO> redisTemplate;// 用于存放用户登录信息的redis 模板类

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        String token = request.getHeader(TOKEN);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json");
        if (PathTools.isSkip(path) || path.contains("/out/")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!StringUtils.hasLength(token)) {
            log.error("{},{}", MESSAGE01, path);
            response.getWriter().write(writeResult(MESSAGE01));
            return;
        }

        boolean pass = verify(token);
        if (!pass) {
            log.error(MESSAGE02);
            response.getWriter().write(writeResult(MESSAGE02));
            return;
        }
        Long userId = userId(token);
        request.getSession().setAttribute(USER_ID, userId);
        LoginVO vo = redisTemplate.opsForValue().get(userId);

        if (Objects.isNull(vo)) {
            log.error(MESSAGE03);
            response.getWriter().write(writeResult(MESSAGE03));
            return;
        }
        User user = vo.getUser();
        if (Objects.isNull(user)) {
            log.error(MESSAGE03);
            response.getWriter().write(writeResult(MESSAGE03));
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String writeResult(String msg) {
        return JsonTools.toString(error(CODE, msg));
    }
}

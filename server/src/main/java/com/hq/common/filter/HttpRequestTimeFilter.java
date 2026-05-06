package com.hq.common.filter;

import com.hq.manage.sys.request.Request;
import com.hq.manage.sys.request.RequestService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author 高振中
 * @summary 【请求时长统计】过滤器
 * @date 2024-05-10 21:44:15
 **/
@Component
public class HttpRequestTimeFilter extends OncePerRequestFilter {

    @Value("${spring.application.name}")
    private String applicationName;// 当前应用名称

    @Autowired
    private RequestService requestService;// 计算逻辑与入库

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if (PathTools.isSwagger(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        Request curRequest = new Request();
        curRequest.setAppName(applicationName);
        curRequest.setPath(path);
        curRequest.setType(PathTools.isFront(path));
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        curRequest.setCurrentMilliSecond(System.currentTimeMillis() - startTime);
        requestService.add(curRequest);
    }
}

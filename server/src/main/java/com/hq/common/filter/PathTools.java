package com.hq.common.filter;

import java.util.ArrayList;
import java.util.List;

import static com.hq.common.Const.FRONT;
import static com.hq.common.Const.STATIC;
import static com.hq.common.Const.SWAGGER;

/**
 * @author 高振中
 * @summary 【路径过滤】工具
 * @date 2024-05-10 21:44:15
 **/
public final class PathTools {
    private static final List<String> SKIPS = List.of("/sys/login");

    /**
     * @方法说明 是否为前端请求
     **/
    public static Byte isFront(String src) {
        for (String path : FRONT) {
            if (src.endsWith(path)) {
                return Byte.valueOf("1");// 前端文件
            }
        }
        // 后端接口
        return Byte.valueOf("2");
    }

    /**
     * @方法说明 swagger路径
     **/
    public static boolean isSwagger(String src) {
        for (String path : SWAGGER) {
            if (src.startsWith(path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @方法说明 路行是否需跳过权限请求
     **/
    public static boolean isSkip(String uri) {
        List<String> skips = new ArrayList<>(SWAGGER);
        skips.addAll(STATIC);
        skips.addAll(SKIPS);
        for (String path : skips) {
            if (uri.startsWith(path)) {
                return true;
            }
        }
        return false;
    }
}

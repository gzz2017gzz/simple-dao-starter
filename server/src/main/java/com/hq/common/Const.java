package com.hq.common;

 

import java.util.List;

import com.auth0.jwt.algorithms.Algorithm;

/**
 * @author 高振中
 * @类说明 系统多实现常量
 * @date 2024-06-25 00:16:58
 **/
public interface Const {
	 /**
     * jwt常量类
     */
    interface Jwt {
        String SECRET = "gzz_gzz@163.com";
        Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
        String USER_ID = "userId";
        String TOKEN = "token";
        int EXPIRE = 2;//token过期时间
    }

    List<String> FRONT = List.of(".js", ".css", ".svg", ".jpg", ".png", ".bmp", ".xml", ".html", ".map", ".json", ".woff", ".mp3");
    List<String> SWAGGER = List.of("/swagger-ui/", "/v3/api-docs", "/swagger-resources", "/csrf");
    List<String> STATIC = List.of("/favicon.ico", "/index.html", "/css/", "/js/", "/fonts/", "/img/", "/logo.png");
    String FORMAT = "%04d";
    int NUM_6 = 6;
    String LIKE = "%";
    String REGEX = "[^0-9]";
    String MESSAGE01 = "token为空,请重新登录!";
    String MESSAGE02 = "token过期,请重新登录!";
    String MESSAGE03 = "token失效,请重新登录!";
    int CODE = 201;
 
    String HTTPS = "https://";
    String FILE_PATH = "sys/";// 当前功能使用这个目录，其它功能可以换成其它目录

}

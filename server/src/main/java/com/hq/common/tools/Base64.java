package com.hq.common.tools;

import static java.util.Base64.getEncoder;
import static java.util.Base64.getMimeDecoder;

/**
 * @author 高振中
 * @summary Base64工具类
 * @date 2024-11-12 21:44:15
 **/
public final class Base64 {
    private Base64() {
    }

    /**
     * 编码
     **/
    public static String encode(String src) {
        return getEncoder().encodeToString(src.getBytes());
    }

    /**
     * 解码
     **/
    public static byte[] decode(String base64) {
        return getMimeDecoder().decode(base64);
    }
}

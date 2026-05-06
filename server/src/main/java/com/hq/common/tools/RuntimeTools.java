package com.hq.common.tools;

import com.hq.common.enums.EnvEnum;

/**
 * @author 吴志建
 * @summary 环境工具
 * @date 2024-10-02 21:44:15
 **/
public final class RuntimeTools {
    private RuntimeTools() {
    }

    public static String active;

    public static boolean isProd() {
        return EnvEnum.PROD.getName().equals(active);
    }

    public static EnvEnum env() {
        return EnvEnum.parse(active);
    }
}

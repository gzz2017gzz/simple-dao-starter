package com.hq.common.config;

import com.hq.common.Const.Jwt;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 高振中
 * @summary 在线文档全局配置
 * @date 2024-06-08 14:22:24
 **/
@Configuration
public class OpenApiConfig {
    /**
     * 描述信息及启用调试时token支持
     **/
    @Bean
    public OpenAPI springOpenAPI() {
        OpenAPI openApi = new OpenAPI().info(new Info().title("simple-dao底座-接口文档").description("管理系统").version("1.0.0-dev"));
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.APIKEY);// 类型
        securityScheme.setName(Jwt.TOKEN);// 请求头的name
        securityScheme.setIn(SecurityScheme.In.HEADER);// token所在位置
        openApi.schemaRequirement(Jwt.TOKEN, securityScheme);
        openApi.addSecurityItem(new SecurityRequirement().addList(Jwt.TOKEN));
        return openApi;
    }

    @Bean
    public GroupedOpenApi baseApi() {
        return GroupedOpenApi.builder().group("基础信息").packagesToScan("com.hq.manage.base").build();
    }

    @Bean
    public GroupedOpenApi sysApi() {
        return GroupedOpenApi.builder().group("系统管理").packagesToScan("com.hq.manage.sys").build();
    }
 

}

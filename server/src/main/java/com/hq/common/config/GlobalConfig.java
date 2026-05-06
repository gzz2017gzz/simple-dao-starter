package com.hq.common.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.simple.common.base.key.Const.INT_1;
import static com.simple.common.base.key.Const.INT_10;
import static com.simple.common.base.key.Const.INT_1000;
import static com.simple.common.base.key.Const.LONG_10;

/**
 * @author 高振中
 * @summary 系统全局配置类
 * @date 2024-05-10 21:44:15
 **/
@Configuration
public class GlobalConfig {
    /**
     * 【线程池】配置
     */
    @Bean
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(INT_1, INT_10, LONG_10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(INT_1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 【响应Long型属性转String】配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(Long.class, ToStringSerializer.instance);
    }

    /**
     * 【RestTemplate】配置
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 【redis】全局配置
     **/
    @Bean
    public <K, V> RedisTemplate<K, V> template(RedisConnectionFactory factory) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}

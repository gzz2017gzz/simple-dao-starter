package com.hq.common.tools;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁工具
 */
public final class LockTools {
    private LockTools() {
    }

    public static RedisTemplate<String, Integer> redisTemplate;

    /**
     * 枷锁
     *
     * @param lockKey 锁标识
     * @return boolean 是否成功
     */
    public static boolean lock(String lockKey) {
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, 1, 30, TimeUnit.MINUTES);
        return locked != null && locked;
    }

    /**
     * 按时间枷锁
     *
     * @param lockKey  锁标识
     * @param time     有效期
     * @param timeUnit 时间单位
     * @return boolean 是否成功
     */
    public static boolean lock(String lockKey, long time, TimeUnit timeUnit) {
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, 1, time, timeUnit);
        return locked != null && locked;
    }

    /**
     * 解锁
     *
     * @param lockKey 锁标识
     */
    public static void unLock(String lockKey) {
        redisTemplate.delete(lockKey);
    }
}

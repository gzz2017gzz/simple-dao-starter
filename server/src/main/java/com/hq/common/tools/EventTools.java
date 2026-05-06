package com.hq.common.tools;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

/**
 * 监听器工具
 */
public final class EventTools {
    private EventTools() {
    }
    public static ApplicationContext applicationContext;

    /**
     * 发送监听器的事件
     *
     * @param event 监听事件
     */
    public static <T extends ApplicationEvent> void send(T event) {
        applicationContext.publishEvent(event);
    }
}

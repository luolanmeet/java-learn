package com.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;

/**
 * 事件总线的门面
 */
public class EventBusService {

    private static final EventBus eventBus = new EventBus();

    private static final AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(10));

    public static void registerListener(Object listener) {
        eventBus.register(listener);
        asyncEventBus.register(listener);
    }

    public static void unRegisterListener(Object listener) {
        eventBus.unregister(listener);
        asyncEventBus.unregister(listener);
    }

    public static void pushSyncEvent(Object event) {
        eventBus.post(event);
    }

    public static void pushAsyncEvent(Object event) {
        asyncEventBus.post(event);
    }

}

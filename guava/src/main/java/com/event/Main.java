package com.event;

import com.event.event.CustomEvent;
import com.event.event.EventType;
import com.event.listener.Listener;

public class Main {

    public static void main(String[] args) {

        Listener listener = new Listener();
        EventBusService.registerListener(listener);
        EventBusService.pushSyncEvent(new CustomEvent(EventType.LOGIN, "cck,123456"));
        EventBusService.pushAsyncEvent(new CustomEvent(EventType.LOGOUT, null));
    }

}

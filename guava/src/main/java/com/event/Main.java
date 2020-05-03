package com.event;

import com.event.event.EventType;
import com.event.event.LoginEvent;
import com.event.event.LogoutEvent;
import com.event.listener.Listener;

public class Main {

    public static void main(String[] args) {

        Listener listener = new Listener();
        EventBusService.registerListener(listener);
        EventBusService.pushSyncEvent(new LoginEvent(EventType.LOGIN, "cck,123456"));
        EventBusService.pushAsyncEvent(new LogoutEvent(EventType.LOGOUT, null));
    }

}

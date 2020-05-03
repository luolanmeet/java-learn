package com.event.listener;

import com.event.event.CustomEvent;
import com.event.event.LoginEvent;
import com.event.event.LogoutEvent;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * 时间监听者
 */
public class Listener {

    @Subscribe
    public void login(LoginEvent event) {
        System.out.println("login " + event.getData());
    }

    // guava的事件默认不是线程安全的，反射调用时会用同步关键字，
    // 加上此注解后会认为是线程安全的，调用时不加同步关键字。见Subscriber
    @AllowConcurrentEvents
    @Subscribe
    public void logout(LogoutEvent event) {
        System.out.println("logout " + event.toString());
    }

    @Subscribe
    public void handleAll(CustomEvent event) {
        System.out.println("handleAll " + event.toString());
    }

}

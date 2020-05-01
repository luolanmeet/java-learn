package com.event.listener;

import com.event.event.CustomEvent;
import com.google.common.eventbus.Subscribe;

/**
 * 时间监听者
 */
public class Listener {

    @Subscribe
    public void method1(CustomEvent event) {

        switch (event.getEventType()) {

            case LOGIN:
                System.out.println("login : " + event.getData());
                break;

            case LOGOUT:
                System.out.println("logout. thread : " + Thread.currentThread().getName());
                break;

            default:
                throw new RuntimeException("unknow event type");
        }
    }

    @Subscribe
    public void method2(CustomEvent event) {
        System.out.println("method2 " + event.toString());
    }

}

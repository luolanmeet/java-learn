package com.event.event;

/**
 * 六一信息科技
 *
 * @author chenken
 * @date 2020/5/2 10:11
 */
public class LogoutEvent extends CustomEvent {

    public LogoutEvent(EventType eventType, Object data) {
        super(eventType, data);
    }

}

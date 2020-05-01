package com.event.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum EventType {

    LOGIN(1,"登录"),
    LOGOUT(2,"登出");

    int type;
    String desc;

}

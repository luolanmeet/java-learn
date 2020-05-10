package pers.bridge.demoOne;

import pers.bridge.demoOne.message.IMessageType;

/**
 * 桥接抽象角色
 */
public class AbastractMessage {

    private IMessageType message;

    public AbastractMessage(IMessageType message) {
        this.message = message;
    }

    public void sendMessage(String message, String toUser) {
        this.message.send(message, toUser);
    }

}

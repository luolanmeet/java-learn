package pers.bridge.demoOne;

import pers.bridge.demoOne.message.IMessageType;

public class NomalMessage extends AbastractMessage {

    public NomalMessage(IMessageType message) {
        super(message);
    }

    @Override
    public void sendMessage(String message, String toUser) {
        message = "【普通消息】 " + message;
        super.sendMessage(message, toUser);
    }

}

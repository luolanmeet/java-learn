package pers.bridge.demoOne;

import pers.bridge.demoOne.message.IMessageType;

public class UrgencyMessage extends AbastractMessage {

    public UrgencyMessage(IMessageType message) {
        super(message);
    }

    @Override
    public void sendMessage(String message, String toUser) {
        message = "【紧急消息】 " + message;
        super.sendMessage(message, toUser);
    }

}

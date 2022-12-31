package pers.bridge.demoOne.message;

public interface IMessageType {

    //发送消息的内容和接收人
    void send(String message, String toUser);

}

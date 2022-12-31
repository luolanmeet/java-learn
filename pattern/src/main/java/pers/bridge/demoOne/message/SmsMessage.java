package pers.bridge.demoOne.message;

public class SmsMessage implements IMessageType {

    @Override
    public void send(String message, String toUser) {
        System.out.println("send sms to " + toUser + " : " + message);
    }

}

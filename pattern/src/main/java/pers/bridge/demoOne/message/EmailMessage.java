package pers.bridge.demoOne.message;

public class EmailMessage implements IMessageType {

    @Override
    public void send(String message, String toUser) {
        System.out.println("send email to " + toUser + " : " + message);
    }

}

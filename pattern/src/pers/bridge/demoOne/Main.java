package pers.bridge.demoOne;

import pers.bridge.demoOne.message.EmailMessage;
import pers.bridge.demoOne.message.IMessageType;
import pers.bridge.demoOne.message.SmsMessage;

public class Main {

    public static void main(String[] args) {

        IMessageType message = new SmsMessage();
        AbastractMessage abstractMessage = new UrgencyMessage(message);
        abstractMessage.sendMessage("系统500", "后端");

        message = new EmailMessage();
        abstractMessage = new NomalMessage(message);
        abstractMessage.sendMessage("服务器负载过高", "运维");
    }

}

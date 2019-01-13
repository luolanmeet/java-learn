package com.origin;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author cck
 */
public class EmailUtil {
    
    private final static String CHARSET = "UTF-8";
    private final static String CONTENT_TYPE = "text/html;charset=UTF-8";
    
    public void sendEmail(Email email) throws Exception {
        
        Properties props = new Properties();
        
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", email.getEmailSMTPHost());  
        props.setProperty("mail.smtp.auth", "true");
        
        Session session = Session.getInstance(props);
        session.setDebug(true);
        MimeMessage message = createMineMessage(session, email);
        
        Transport transport = session.getTransport();
        transport.connect(email.getSenderAccount(), email.getSenderPassword());
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    private MimeMessage createMineMessage(Session session, Email email) throws MessagingException, Exception {
        
        MimeMessage message = new MimeMessage(session);
        
        // 发信人
        message.setFrom(getAddress(email.getSenderAccount(), email.getSenderName()));
        // 收信人
        message.setRecipient(MimeMessage.RecipientType.TO, 
                getAddress(email.getReceiverAccount(), email.getReceiverName()));
        
        // 抄送给自己, 使用网易发送邮件时,可能会报 554 DT:SPM, 只要抄送一份给自己就不会了
//        message.setRecipient(MimeMessage.RecipientType.CC, 
//                getAddress(email.getSenderAccount(), email.getSenderName()));
        
        message.setSubject(email.getTile(), CHARSET);
        message.setContent(email.getContent(), CONTENT_TYPE);
        
        message.setSentDate(email.getSendTime());
        message.saveChanges();
        return message;
    }

    private InternetAddress getAddress(String address, String name) throws Exception {
        return new InternetAddress(address, name, CHARSET);
    }
        
}

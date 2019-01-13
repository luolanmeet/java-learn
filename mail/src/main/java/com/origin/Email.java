package com.origin;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Email {
    
    @NonNull
    /** 发信人名称 */
    private String senderName;
    @NonNull
    /** 发信人地址 */
    private String senderAccount;
    @NonNull
    /** 发信人邮箱密码 */
    private String senderPassword;
    @NonNull
    /** 发信人SMTP地址 */
    private String emailSMTPHost; 
    
    @NonNull
    /** 收信人名称 */
    private String receiverName;
    @NonNull
    /** 收信人地址 */
    private String receiverAccount;
    
    @NonNull
    /** 信的标题 */
    private String tile;
    @NonNull
    /** 信的内容 */
    private String content;
    @NonNull
    /** 信的发送的时间 */
    private Date sendTime;
    
}

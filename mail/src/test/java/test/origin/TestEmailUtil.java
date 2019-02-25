package test.origin;

import com.origin.Email;
import com.origin.EmailUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * 
 * @author cck
 */
public class TestEmailUtil {

    private EmailUtil util;
    private Email email;
    
    private String content = "<table border='0' cellspacing='0' cellpadding='0' width='552' style='border-radius:4px;border:1px solid #dedede;margin:0 auto;background-color:#ffffff'> <tbody> <tr> <td align='center' style='padding:20px 25px 20px;background-color:#34495E;color:white;border-radius:4px 4px 0 0'> <div style='color:white'> <a style='color:#ffffff;text-decoration: none;' href='https://wuzhi.me' target='_blank'><span style='margin:0;font-size:22px;'>所愿 - 记录生活点滴</span></a> </div> </td> </tr> <tr> <td style='padding:20px 25px 0px; font-size:16px;line-height:2em;'> <p> </p><div style='margin-bottom: 10px;'>亲爱的 luoluo：</div> 感谢您注册所愿，期待在未来的日子里您和我们一起记录生活点滴。</td> </tr> <tr> <td style='padding:20px 25px 20px'> <table width='100%' cellspacing='0' cellpadding='0' border='0' style='margin: 0; padding: 0; border-top: 1px solid #ccc; border-right: 0 !important; border-left: 0 !important; border-bottom: 0 !important;'> <tbody> <tr> <td> <p style='text-align: center;font-size: 12px; line-height: 16px; color: #7e7d7e; margin: 14px 0 10px 0; padding: 0;'> <a style='text-decoration: none; color: #7e7d7e' href='localhost:8080' target='_blank'>所愿 © 2010-2018</a> &nbsp;|</p> <p style='text-align: center;font-size: 12px; line-height: 11px; color: #7e7d7e;margin:0px;'> 如有使用问题或意见反馈，欢迎随时联系我们</p> </td> </tr> </tbody> </table> </td> </tr> </tbody> </table>";
    
    @Before
    public void before() {
        
        util = new EmailUtil();
        email = Email.builder()
                    .senderAccount("mainem@163.com")
                    .senderName("cck")
                    .receiverAccount("3409438184@qq.com")
                    .senderPassword("")
                    .emailSMTPHost("smtp.163.com")
                    .receiverName("")
                    .sendTime(new Date())
                    .tile("所愿")
                    .content(content)
                    .build();
    }
    
    @Test
    public void testSendEmail() throws Exception {
        util.sendEmail(email);
    }
}

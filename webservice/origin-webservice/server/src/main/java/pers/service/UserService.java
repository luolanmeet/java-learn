package pers.service;

import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @auther ken.ck
 * @date 2022/4/25 10:39
 */
//@BindingType(SOAPBinding.SOAP12HTTP_BINDING) // 默认 soap是 1.1版本
@WebService // 标识这是一个WebService
public class UserService {

    public User changeUser(User user, String prefix) {
        System.out.println("传入的参数：" + user + " " + prefix);
        user.setId(prefix + user.getId());
        user.setName(prefix + user.getName());
        return user;
    }

    public String queryUser(String xml) {

        String result = null;

        try {
            System.out.println("传入的参数：" + xml);

            // xml转换为对象 JAXBContext用于对象和xml之间的转换
            JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader stringReader = new StringReader(xml);
            User user = (User) unmarshaller.unmarshal(stringReader);
            System.out.println("传入的对象：" + user.toString());

            // 返回数据
            user = new User();
            user.setId("1");
            user.setName("尼卡");

            //对象转换成xml
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(user, stringWriter);
            System.out.println("返回的内容：" + stringWriter.toString());
            result = stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

}

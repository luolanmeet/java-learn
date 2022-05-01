package pers.service;

import org.springframework.stereotype.Component;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @auther ken.ck
 * @date 2022/4/25 19:55
 */
@Component
@WebService
public class UserService {

    public @WebResult(name = "user") User changeUser(@WebParam(name = "user")User user, String prefix) {
        System.out.println("传入的参数：" + user + " " + prefix);
        user.setId(prefix + user.getId());
        user.setName(prefix + user.getName());
        return user;
    }

    public String queryUser(@WebParam(name = "xml") String xml) {
        System.out.println("传入的参数：" + xml);
        return "hi " + xml;
    }

}

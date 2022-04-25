package pers.service;

import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * @auther ken.ck
 * @date 2022/4/25 19:55
 */
@Component
@WebService
public class UserService {

    public String queryUser(String xml) {
        System.out.println("传入的参数：" + xml);
        return "hi " + xml;
    }

}

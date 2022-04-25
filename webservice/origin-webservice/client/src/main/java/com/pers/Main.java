package com.pers;

import com.pers.client.UserService;
import com.pers.client.UserServiceService;

/**
 * @auther ken.ck
 * @date 2022/4/25 11:02
 */
public class Main {

    public static void main(String[] args) {
        UserServiceService userServiceService = new UserServiceService();
        UserService userService = userServiceService.getUserServicePort();
        String xmlRequest = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><user><id>1001</id><name>索隆</name></user>";
        String xmlResponse = userService.queryUser(xmlRequest);
        System.out.println(xmlResponse);
    }

}

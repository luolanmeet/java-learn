package pers.service;

import pers.IUserService;

public class UserServiceImpl implements IUserService {

    @Override
    public String sayHello(String str) {
        System.out.println("hello " + str);
        return "hello " + str;
    }

}

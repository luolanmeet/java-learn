package pers.mock;

import pers.IUserService;

public class UserServiceMock implements IUserService {

    @Override
    public String sayHello(String str) {
        return "sayHello 发生异常";
    }
}

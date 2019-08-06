package pers.spring.test.obj;

import lombok.Getter;
import pers.spring.formework.beans.factory.annotation.Autowired;

public class UserService {

    @Getter
    @Autowired
    private UserDAO userDAO;

    public void sayHello(String name) {
        System.out.println("hello " + name);
    }

}

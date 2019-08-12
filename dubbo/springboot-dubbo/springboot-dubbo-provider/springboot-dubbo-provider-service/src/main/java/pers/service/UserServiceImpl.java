package pers.service;

import org.apache.dubbo.config.annotation.Service;
import pers.IUserService;

/**
 *
 * @author cck
 */
@Service
public class UserServiceImpl implements IUserService {

    @Override
    public String sayHello(String str) {
        System.out.println("hello " + str);
        return "hello " + str;
    }

}

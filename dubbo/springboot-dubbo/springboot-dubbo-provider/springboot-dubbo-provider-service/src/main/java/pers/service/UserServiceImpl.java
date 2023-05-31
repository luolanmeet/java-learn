package pers.service;

import org.apache.dubbo.config.annotation.Service;
import pers.IUserService;

/**
 *
 * @author cck
 */
@Service(loadbalance="roundrobin", weight=8, cluster="failsafe")
public class UserServiceImpl implements IUserService {

    @Override
    public String sayHello(String str) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("hello " + str);
        return "hello " + str;
    }

}

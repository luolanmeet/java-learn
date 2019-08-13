package pers.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pers.IUserService;

/**
 * 
 * @author cck
 */
@RestController
public class UserController {

    // 容错指定为 failfast ， 才不会再重试2次（默认）
    @Reference(mock="pers.mock.UserServiceMock", timeout=500, cluster = "failfast")
    private IUserService userService;
    
    @GetMapping("hello/{str}")
    public String sayHello(@PathVariable("str") String str) {
        return userService.sayHello(str);
    }
    

}

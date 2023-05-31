package pers.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pers.IUserService;
import pers.service.DubboGenericServiceImpl;
import pers.service.RpcProviderMsgDTO;

/**
 * 
 * @author cck
 */
@RestController
public class UserController {

    // 容错指定为 failfast ， 才不会再重试2次（默认）
//    @Reference(mock="pers.mock.UserServiceMock", timeout=500, cluster = "failfast")
    @Reference(check = false)
    private IUserService userService;

    // 泛化调用
    @Autowired
    private DubboGenericServiceImpl dubboGenericService;

    // localhost:8080/hello/cck
    @GetMapping("hello/{str}")
    public String sayHello(@PathVariable("str") String str) {
        return userService.sayHello(str);
    }

    // localhost:8080/hello2/cck
    @GetMapping("hello2/{str}")
    public String sayHello2(@PathVariable("str") String str) {
        // 泛化调用
        Object sayHello = dubboGenericService.genericInvoke(
                "pers.IUserService",
                "",
                "sayHello",
                new String[]{"java.lang.String"},
                new Object[]{str},
                3000);
        return sayHello == null ? null : sayHello.toString();
    }

    // localhost:8080/hello3
    @GetMapping("hello3")
    public RpcProviderMsgDTO getProvider() {
        // 泛化调用
        return dubboGenericService.getProviderMsg(
                "pers.IUserService",
                "",
                3000);
    }

}

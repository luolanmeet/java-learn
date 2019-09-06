package pers.config.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("config")
public class ConfigController {

    @NacosValue(value = "${hostname:localhost}", autoRefreshed = true)
    private String hostname;

    @NacosValue(value = "${password:1234567}", autoRefreshed = true)
    private String password;

    @RequestMapping("get")
    public String get() {
        return hostname + " " + password;
    }

}

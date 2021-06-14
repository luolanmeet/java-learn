package pers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther ken.ck
 * @date 2021/6/14 11:04
 */
@Controller
public class CorsController {

    @RequestMapping(path = "testCors")
    public String testCors() {
        return "/index.html";
    }

}

package pers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auther ken.ck
 * @date 2021/6/14 02:22
 */
@Controller
@RequestMapping(path = "status")
public class HttpStatusCodeController {

    @ResponseBody
    @RequestMapping(path = "200")
    public String status200() {
        // ResponseBody，直接返回数据，
        // 会认为本次请求成功，http状态码会以200返回
        return "status 200";
    }

    @RequestMapping(path = "500")
    public void status500() {
        if (1 == 1) {
            // 当处理请求的逻辑跑出异常时，http状态码会以500返回
            // 服务器内部错误，
            throw new RuntimeException("status 500");
        }
    }

}

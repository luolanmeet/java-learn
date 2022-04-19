package com.pers.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @auther ken.ck
 * @date 2022/4/19 12:24
 */
@FeignClient("HelloServer")
public interface HelloClient {
    @RequestMapping(value = "/", method = GET)
    String hello();
}

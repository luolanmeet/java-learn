package pers;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 
 * @author cck
 */
@FeignClient(name = "hello-service")
public interface IHelloService {
    
    @PostMapping("hello")
    String hello(String name);

}

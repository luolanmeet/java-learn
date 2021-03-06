package pers;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author cck
 */
@FeignClient(name = "hello-service")
public interface IHelloWorldService {
    
    @GetMapping("helloWorld")
    String helloWorld();
    
}

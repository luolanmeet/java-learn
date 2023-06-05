package per.demo2.health;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther ken.ck
 * @date 2023/6/2 14:50
 */
@RestController
@RequestMapping("/")
public class TestController {

    @RequestMapping("hello/{num}")
    public String hello(@PathVariable("num") Integer num) {

        if (num != null && num == 10) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return "hello" + num;
    }

}

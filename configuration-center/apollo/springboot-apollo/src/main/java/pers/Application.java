package pers;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import pers.config.AppConfig;
import pers.config.AppConfig2;

import java.util.concurrent.TimeUnit;

/**
 *  开启apollo支持
 *  https://github.com/ctripcorp/apollo/wiki/Java%E5%AE%A2%E6%88%B7%E7%AB%AF%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97
 *  用 apollo.bootstrap.enabled = true 开启也行
 */
@EnableApolloConfig
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new SpringApplicationBuilder(Application.class).run(args);
        AppConfig config = context.getBean(AppConfig.class);
        System.out.println(config);

        AppConfig2 config2 = context.getBean(AppConfig2.class);
        System.out.println(config2);

        while (true) {
            TimeUnit.SECONDS.sleep(2);

            // 配置会自动更新为最新的值
            System.out.println(config2);

//            config2 = context.getBean(AppConfig2.class);
//            System.out.println(config2);
        }

    }

}

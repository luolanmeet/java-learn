package pers;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import pers.config.AppConfig;
import pers.config.AppConfig2;

import java.util.concurrent.TimeUnit;

// 开启apollo支持
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

package pers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pers.config.DbUserConfig;

/**
 * 
 * @author cck
 */
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplication()
                .run(Application.class, args);
    
        // 获取template
        ZkConfigCenterTemplate template = applicationContext.getBean(ZkConfigCenterTemplate.class);
        System.out.println(template);
        
        // 获取zk配置
        DbUserConfig dbUserConfig = template.getConfig("/db-user-config", DbUserConfig.class);
        System.out.println(dbUserConfig);
    
    }

}

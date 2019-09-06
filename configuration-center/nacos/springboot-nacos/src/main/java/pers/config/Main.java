package pers.config;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NacosPropertySource(dataId = "database", autoRefreshed = true)
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}

package pers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@PropertySource({"classpath:application-${spring.profiles.active}.properties"})
public class SMApplication {

	public static void main(String[] args) {
		SpringApplication.run(SMApplication.class, args);
	}
}
package per;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(Main.class)
                .web(WebApplicationType.NONE).build()
                .run(args);
    }

}

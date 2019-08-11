package pers;

import org.apache.dubbo.container.Main;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        Main.main(new String[]{"spring", "log4j"});

        /*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                        new String[] {"META-INF/spring/provider.xml"});
        context.start();
        System.in.read(); // 按任意键退出*/
    }

}

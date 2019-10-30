package pers.resolver;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

/**
 * 自定义协议解析器
 * 加载资源用的
 */
public class MyProtocolResolver implements ProtocolResolver {

    public static final String MY_URL_PREFIX = "my:";

    @Override
    public Resource resolve(String location, ResourceLoader resourceLoader) {

        if (location.startsWith(MY_URL_PREFIX)) {
            System.out.println("MyProtocolResolver#resolve");
            return resourceLoader.getResource(
                    location.replace(MY_URL_PREFIX, ResourceUtils.CLASSPATH_URL_PREFIX));
        }

        return null;
    }

    public static void main(String[] args) {

        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        defaultResourceLoader.addProtocolResolver(new MyProtocolResolver());

        Resource resource = defaultResourceLoader.getResource("my:spring.xml");
        System.out.println(resource.getFilename());
    }

}

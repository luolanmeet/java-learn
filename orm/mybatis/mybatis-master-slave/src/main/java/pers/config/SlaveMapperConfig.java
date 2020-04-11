package pers.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import pers.util.ClassScanUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Component
public class SlaveMapperConfig implements BeanDefinitionRegistryPostProcessor {

    private final static String CONFIG_NAME = "master-slave.properties";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        Properties properties = new Properties();

        ClassPathResource cpr = new ClassPathResource(CONFIG_NAME);
        try {
            properties.load(cpr.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (properties.keySet().isEmpty()) {
            System.out.println("no exist master-slave config");
            return ;
        }

        // 拿到一个包路径，以及该包路径下的Mapper要使用的sqlSessionFactoryTemplate
        String backagePath = (String) properties.get("backagePath");
        String sqlSessionTemplate = (String) properties.get("sqlSessionTemplate");

        // 获取这些BeanDefinition,复制一份，并且设置新的sqlSessionFactoryTemplate
        Map<String, String> beanNameMap = ClassScanUtil.scan(backagePath);

        for (Map.Entry<String, String> entry : beanNameMap.entrySet()) {

            BeanDefinition beanDefinition = getBeanDefinition(registry, entry);
            if (beanDefinition == null) {
                System.out.println("can not find beandefinition : " + entry);
                continue;
            }

            GenericBeanDefinition newBeanDefinition1 = new GenericBeanDefinition(beanDefinition);
            newBeanDefinition1.getPropertyValues().add(
                    "sqlSessionTemplate",
                    new RuntimeBeanReference(sqlSessionTemplate));

            // 注册到spring中
            String newBeanName = entry.getValue();
            newBeanName = "slave" + newBeanName.substring(0, 1).toUpperCase() + newBeanName.substring(1);
            registry.registerBeanDefinition(newBeanName, newBeanDefinition1);
        }

    }

    public BeanDefinition getBeanDefinition(
            BeanDefinitionRegistry registry,
            Map.Entry<String, String> entry) {

        // 原始类名
        String beanName = entry.getValue();
        BeanDefinition beanDefinition = getBeanDefinition(registry, beanName);
        if (beanDefinition != null) return beanDefinition;

        // 原类名小写
        beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
        beanDefinition = getBeanDefinition(registry, beanName);
        if (beanDefinition != null) return beanDefinition;

        // 类全路径
        beanName = entry.getKey();
        beanDefinition = getBeanDefinition(registry, beanName);

        return beanDefinition;
    }

    public BeanDefinition getBeanDefinition(
            BeanDefinitionRegistry registry, String beanName) {

        try {
            return registry.getBeanDefinition(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

}

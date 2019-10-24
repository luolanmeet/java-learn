package pers.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 	Bean实例化完成之前调用，可以修改Bean的定义信息
 * 	启动之后只会执行一次
 * 	实现 Ordered 接口的化，多个BeanFactoryPostProcessor按顺序执行，值越小越先执行
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        System.out.println("BeanFactoryPostProcessor#postProcessBeanFactory");

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("studentBean");

        // 可以修改作用域这些

        // 修改属性值
        MutablePropertyValues pv = beanDefinition.getPropertyValues();
        pv.addPropertyValue("name", "ck");
    }

}

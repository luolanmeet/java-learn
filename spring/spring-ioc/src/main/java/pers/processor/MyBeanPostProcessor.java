package pers.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import pers.bean.Student;

/**
 * Bean初始化前后调用，可以修改Bean实例的信息，不能修改Bean定义信息
 * 实现 Ordered 接口的话，多个BeanPostProcessor按顺序执行，值越小越先执行
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    
    /**
     * Bean初始化前调用
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        
        if (bean instanceof Student) {
            ((Student) bean).setAge(((Student) bean).getAge() + 1);
            System.out.println("BeanPostProcessor#postProcessBeforeInitialization");
        }

        return bean;
    }
    
    /**
     * Bean初始化后调用
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof Student) {
            System.out.println("BeanPostProcessor#postProcessAfterInitialization");
        }

        return bean;
    }
    
}

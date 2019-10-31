package pers.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

@Data
@ToString(exclude = {"beanFactory", "classLoader", "applicationContext", "applicationEventPublisher"})
public class Student implements
        BeanNameAware, BeanClassLoaderAware,
        BeanFactoryAware, ApplicationContextAware,
        ApplicationEventPublisherAware,
        InitializingBean, DisposableBean {

    private Integer age;
    
    private String name;
    
    private String beanName;
    
    private BeanFactory beanFactory;
    
    private ClassLoader classLoader;
    
    private ApplicationContext applicationContext;
    
    private ApplicationEventPublisher applicationEventPublisher;
    
    /**
     * 可以获取bean在BeanFactory中的名子
     */
    @Override
    public void setBeanName(String name) {
        System.out.println("BeanNameAware#setBeanName");
        beanName = name;
    }
    
    /**
     * 可以获取bean的ClassLoader
     */
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("BeanClassLoaderAware#setBeanClassLoader");
        this.classLoader = classLoader;
    }
    
    /**
     * 可以拿到bean所在的BeanFactory
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware#setBeanFactory");
        this.beanFactory = beanFactory;
    }
    
    /**
     * 可以拿到用于事件发布的对象
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        System.out.println("ApplicationEventPublisherAware#setApplicationEventPublisher");
    }
    
    /**
     * 可以拿到ApplicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContextAware#setApplicationContext");
        this.applicationContext = applicationContext;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet");
    }
    
    public void initMethod() {
        System.out.println("initMethod");
    }
    
    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy");
    }
    
    public void destroyMethod() {
        System.out.println("destroyMethod");
    }
    
}

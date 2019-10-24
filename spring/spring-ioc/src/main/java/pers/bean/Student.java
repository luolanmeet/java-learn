package pers.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Data
@ToString(exclude = {"beanFactory", "applicationContext"})
public class Student implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {

    private Integer age;
    
    private String name;
    
    private String beanName;
    
    private BeanFactory beanFactory;
    
    private ApplicationContext applicationContext;
    
    /**
     * 可以获取bean在BeanFactory中的名子
     */
    @Override
    public void setBeanName(String name) {
        System.out.println("BeanNameAware#setBeanName");
        beanName = name;
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
     * 可以拿到ApplicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContextAware#setApplicationContext");
        this.applicationContext = applicationContext;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("student bean InitializingBean#afterPropertiesSet");
    }
    
    public void initMethod() {
        System.out.println("student bean initMethod");
    }
    
    @Override
    public void destroy() throws Exception {
        System.out.println("student bean DisposableBean#destroy");
    }
    
    public void destroyMethod() {
        System.out.println("student bean destroyMethod");
    }
    
}

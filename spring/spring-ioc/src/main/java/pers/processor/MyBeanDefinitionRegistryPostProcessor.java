package pers.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import pers.bean.ITeacher;
import pers.bean.TeacherFactoryBean;

public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    
    protected BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
    
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        
        // 可以自己注册BeanDefinition
        Class interfaceClass = ITeacher.class;
        
        // 创建一个BeanDefinitionBuilder
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(interfaceClass);
        // 创建一个BeanDefinition
        GenericBeanDefinition definition = (GenericBeanDefinition) beanDefinitionBuilder.getRawBeanDefinition();
        // 添加属性，最终会设置到FactoryBean中
        definition.getPropertyValues().add("interfaceClass", interfaceClass);
        // 设置bean工厂
        definition.setBeanClass(TeacherFactoryBean.class);
        // 设置注入方式
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        
        // 获取beanName
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(interfaceClass);
        String beanName = beanNameGenerator.generateBeanName(rootBeanDefinition, registry);
    
        // 注册BeanDefinition
        registry.registerBeanDefinition(beanName, definition);
        
        System.out.println("BeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry");
    }
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("BeanDefinitionRegistryPostProcessor#postProcessBeanFactory");
    }
    
}

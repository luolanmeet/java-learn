package pers.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import pers.bean.Student;

/**
 * Bean实例化前后
 * 注意不要重写错方法，InstantiationAwareBeanPostProcessor也继承了BeanPostProcessor接口
 */
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    
    /**
     * bean实例化之前调用
     * 可替换成代理对象
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        
        if (beanClass.equals(Student.class)) {
            System.out.println("InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation");
        }
        return null;
    }
    
    /**
     * bean实例化之后调用
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        
        if (Student.class.equals(bean.getClass())) {
            System.out.println("InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation");
        }
        return true;
    }

}

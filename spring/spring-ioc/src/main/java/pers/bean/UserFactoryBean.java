package pers.bean;

import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;

/**
 * FactoryBean接口：可以返回bean的实例的工厂bean
 *
 * BeanFactory接口中有一个字符常量String FACTORY_BEAN_PREFIX = "&";
 * 当我们去获取FactoryBean类型的bean时，如果beanName不加&则获取到对应bean的实例（getObject()）
 * 如果beanName加上&，则获取到FactoryBean本身的实例
 */
public class UserFactoryBean implements FactoryBean<User> {
    
    @Setter
    private String userInfo;
    
    /**
     * 工厂，创建实例
     */
    @Override
    public User getObject() throws Exception {
    
        String[] strs = userInfo.split(",");
    
        return User.builder()
                .age(Integer.valueOf(strs[0]))
                .name(strs[1])
                .build();
    }
    
    /**
     * 返回创建的实例的类型
     */
    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
    
    /**
     * 控制是否是单例
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}

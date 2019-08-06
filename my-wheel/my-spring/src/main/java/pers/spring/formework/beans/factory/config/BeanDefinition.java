package pers.spring.formework.beans.factory.config;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

/**
 * 事实上，spring中BeanDefinition是一个接口
 */
@Data
public class BeanDefinition {

    private String beanClassName;

    private boolean lazyInit = false;

    private String factoryBeanName;

}

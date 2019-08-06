package pers.spring.formework.beans.factory.support;

import pers.spring.formework.beans.factory.config.BeanDefinition;
import pers.spring.formework.context.support.AbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 和spring一样，这里存储公共的逻辑
 */
public class DefaultListableBeanFactory extends AbstractApplicationContext {

    /** Map of bean definition objects, keyed by bean name */
    protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(256);

}

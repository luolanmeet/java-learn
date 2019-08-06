package pers.spring.formework.context;

import pers.spring.formework.beans.BeanFactory;
import pers.spring.formework.beans.BeanWrapper;
import pers.spring.formework.beans.factory.annotation.Autowired;
import pers.spring.formework.beans.factory.config.BeanDefinition;
import pers.spring.formework.beans.factory.support.BeanDefinitionReader;
import pers.spring.formework.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 事实上，spring中ApplicationContext是一个接口
 * 这里不区分是xml或是annotion的上下文
 */
public class ApplicationContext extends DefaultListableBeanFactory implements BeanFactory {

    private String location;
    private BeanDefinitionReader definitionReader;

    // 单例的IOC容器
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    // 通用的IOC容器
    private Map<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    public ApplicationContext (String location) {
        this.location = location;
        this.refresh();
    }

    @Override
    public void refresh() {

        // 实例化Reader
        definitionReader = new BeanDefinitionReader(location);

        // 加载配置文件，封装BeanDefinition
        List<BeanDefinition> beanDefinitions = definitionReader.loadBeanDefinitions();

        // 注册，把配置信息放到容器里边
        doRegisterBeanDefinitions(beanDefinitions);

        // 把不是延迟加载的，提前初始化
        doAutowired();
    }

    private void doAutowired() {

        for (Map.Entry<String, BeanDefinition> entry : super.beanDefinitionMap.entrySet()) {
            if (!entry.getValue().isLazyInit()) {
                getBean(entry.getKey());
            }
        }

        System.out.println(this.factoryBeanInstanceCache);
        System.out.println(this.singletonObjects);

        // 注入依赖 DI
        for (Map.Entry<String, BeanDefinition> entry : super.beanDefinitionMap.entrySet()) {
            populateBean(entry.getKey(), entry.getValue(), factoryBeanInstanceCache.get(entry.getKey()));
        }
    }

    private void doRegisterBeanDefinitions(List<BeanDefinition> beanDefinitions) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
        System.out.println(super.beanDefinitionMap);
    }

    @Override
    public Object getBean(String beanName) {

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

        // 初始化bean
        BeanWrapper beanWrapper = instantitateBean(beanName, beanDefinition);

        // 保存
        factoryBeanInstanceCache.put(beanName, beanWrapper);

        return beanWrapper.getWrappedInstance();
    }

    private void populateBean(String beanName, BeanDefinition beanDefinition, BeanWrapper beanWrapper) {

        Class<?> clazz = beanWrapper.getWrappedClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            if (!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }

            Autowired annotation = field.getAnnotation(Autowired.class);
            String autowiredBeanName = annotation.valuse();

            if ("".equals(autowiredBeanName)) {
                autowiredBeanName = field.getType().getName();
                autowiredBeanName = lowerFirstCase(field.getType().getName().substring(autowiredBeanName.lastIndexOf(".") + 1));
            }

            field.setAccessible(true);

            try {
                field.set(beanWrapper.getWrappedInstance(),
                        factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private BeanWrapper instantitateBean(String beanName, BeanDefinition beanDefinition) {

        // 拿到类的全类名
        String beanClassName = beanDefinition.getBeanClassName();

        // 反射创建实例，假设都是单例
        Object instance = null;
        try {

            instance = singletonObjects.get(beanClassName);

            if (instance == null) {
                Class<?>  clazz = Class.forName(beanClassName);
                instance = clazz.newInstance();
                singletonObjects.put(beanClassName, instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 封装到beanWrapper
        BeanWrapper beanWrapper = new BeanWrapper(instance);

        // beanWrapper 存到IOC容器
        singletonObjects.put(beanClassName, instance);

        return beanWrapper;
    }

    private String lowerFirstCase(String str){
        char [] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

}

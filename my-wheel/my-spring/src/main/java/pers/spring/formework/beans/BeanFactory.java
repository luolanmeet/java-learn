package pers.spring.formework.beans;

public interface BeanFactory {

    /**
     * 根据 name 从IOC容器中获取一个实例
     * 如果 Bean 是延迟实例化的，此时会实例化
     * 如果 Bean 是非延迟实例化的，则创建IOC容器时就初始化
     * getBean -> doGetBean
     * @param name
     * @return
     */
    Object getBean(String name);

}

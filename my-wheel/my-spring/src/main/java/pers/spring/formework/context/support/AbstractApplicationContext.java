package pers.spring.formework.context.support;

/**
 *  模版模式
 *  定义了refresh，事实上spring是
 *  ConfigurableApplicationContext接口 声明了 refresh 方法
 */
public abstract class AbstractApplicationContext {

    public void refresh() { };

}

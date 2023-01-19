package pers.decorator;

/**
 * 基础装饰器类
 */
public class BaseDecoratorCake extends BaseCake{

    protected BaseCake baseCake;

    public BaseDecoratorCake(BaseCake baseCake) {
        this.baseCake = baseCake;
    }

    /**
     * 注意，这里不是用父类的方法
     */
    @Override
    public String getMsg() {
        return baseCake.getMsg();
    }

    /**
     * 注意，这里不是用父类的方法
     */
    @Override
    public Integer getCalorie() {
        return baseCake.getCalorie();
    }
}

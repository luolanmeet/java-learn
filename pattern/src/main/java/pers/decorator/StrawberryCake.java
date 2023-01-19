package pers.decorator;

/**
 * 为被装饰的类添加具体的功能
 */
public class StrawberryCake extends BaseDecoratorCake {

    public StrawberryCake(BaseCake baseCake) {
        super(baseCake);
    }

    @Override
    public String getMsg() {
        return super.getMsg() + "+草莓";
    }

    @Override
    public Integer getCalorie() {
        return super.getCalorie() + 50;
    }

}

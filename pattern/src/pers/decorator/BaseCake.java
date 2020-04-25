package pers.decorator;

/**
 * 被装饰的类，提供基本的功能
 */
public class BaseCake extends Cake {

    @Override
    public String getMsg() {
        return "蛋糕";
    }

    @Override
    public Integer getCalorie() {
        return 100;
    }
}

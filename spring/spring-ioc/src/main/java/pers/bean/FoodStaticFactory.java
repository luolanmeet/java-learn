package pers.bean;

/**
 * 静态工厂方法
 */
public class FoodStaticFactory {

    public static Food getFood(String name) {
        return new Food(name);
    }

}

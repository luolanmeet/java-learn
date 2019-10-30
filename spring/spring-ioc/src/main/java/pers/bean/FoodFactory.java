package pers.bean;

/**
 * 实例工厂方法
 */
public class FoodFactory {
    
    public Food getFood(String name) {
        return new Food(name);
    }
    
}

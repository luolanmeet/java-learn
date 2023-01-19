package pers.builder.demo.two;

import java.util.EnumSet;
import java.util.Set;

/**
 * 类层次结构的构造器
 * @author cck
 * @date 2020/11/8 14:20
 */
public abstract class Pizza {

    public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE}
    final Set<Topping> toppings;

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone();
    }

    abstract static class Builder<T extends Builder<T>> {

        private EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping (Topping topping) {
            toppings.add(topping);
            return self();
            // 这里是抽象类，没法返回this，因此需要在子类中实现
//            return this;
        }

        abstract Pizza build();

        // 子类必须重写此方法返回this
        protected abstract T self();
    }

}

package pers.builder.demo.two;

/**
 * @author cck
 * @date 2020/11/8 14:43
 */
public class Main {

    public static void main(String[] args) {

        Pizza pizza = new NyPizza.Builder(NyPizza.Size.LARGE)
                .addTopping(Pizza.Topping.MUSHROOM)
                .addTopping(Pizza.Topping.ONION)
                .build();

        pizza = new Calzone.Builder()
                .sauceInside()
                .addTopping(Pizza.Topping.ONION)
                .build();
    }

}

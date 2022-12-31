package pers.builder.demo.one;

/**
 * @author cck
 * @date 2020/11/8 14:16
 */
public class Main {

    public static void main(String[] args) {
        NutritionFacts nutritionFacts = new NutritionFacts.Builder(240, 123)
                .fat(44)
                .dodium(53)
                .build();
        System.out.println(nutritionFacts);
    }

}
